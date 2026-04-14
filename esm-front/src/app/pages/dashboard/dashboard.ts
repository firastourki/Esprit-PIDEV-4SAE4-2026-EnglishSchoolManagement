import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { AssessmentService } from '../../services/assessment.service';
import { ResourcesService } from '../../services/resource.service';
import { forkJoin, of, timer } from 'rxjs';
import { catchError, retry, switchMap } from 'rxjs/operators';

interface ActivityItem {
  icon: string;
  message: string;
  time: string;
}

interface UpcomingAlert {
  title: string;
  courseName: string;
  startDate: string;
  hoursLeft: number;
  urgency: 'soon' | 'urgent';
}

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})
export class Dashboard implements OnInit {

  totalStudents = 120;
  totalCourses = 8;
  totalAssessments = 0;
  totalResources = 0;

  recentActivity: ActivityItem[] = [];
  upcomingAlerts: UpcomingAlert[] = [];

  // Keep last known good values — never reset to 0 on error
  private _lastAssessments = 0;
  private _lastResources = 0;

  constructor(
    private assessmentService: AssessmentService,
    private resourcesService: ResourcesService,
    private cdr: ChangeDetectorRef
  ) { }

  ngOnInit(): void {
    this.loadData();
  }

  loadData(): void {
    this.assessmentService.getAll()
      .pipe(
        retry(2),                    // retry 2 times before giving up
        catchError(() => of(null))   // null = request failed, keep old values
      )
      .subscribe(data => {
        // ── If request failed keep last known values ──────────────────────
        if (data === null) {
          this.totalAssessments = this._lastAssessments;
          this.totalResources = this._lastResources;
          this.cdr.detectChanges();
          return;
        }

        // ── Assessments count ─────────────────────────────────────────────
        this._lastAssessments = data.length;
        this.totalAssessments = data.length;

        // ── Upcoming alerts (1h → 48h) ────────────────────────────────────
        const now = Date.now();
        this.upcomingAlerts = data
          .filter(a => a.startDate)
          .map(a => {
            const diff = new Date(a.startDate!).getTime() - now;
            const hoursLeft = diff / 3600000;
            return { a, hoursLeft };
          })
          .filter(({ hoursLeft }) => hoursLeft >= 1 && hoursLeft <= 48)
          .sort((x, y) => x.hoursLeft - y.hoursLeft)
          .map(({ a, hoursLeft }) => ({
            title: a.title,
            courseName: a.courseName,
            startDate: a.startDate!,
            hoursLeft: Math.floor(hoursLeft),
            urgency: (hoursLeft <= 6 ? 'urgent' : 'soon') as 'urgent' | 'soon'
          }));

        // ── Recent activity from assessments ──────────────────────────────
        const assessmentActivity: ActivityItem[] = [...data]
          .reverse()
          .slice(0, 3)
          .map(a => ({
            icon: '📝',
            message: `Assessment "${a.title}" — ${a.status}`,
            time: a.startDate ? this.formatRelative(a.startDate as string) : 'recently'
          }));

        this.recentActivity = assessmentActivity;
        this.cdr.detectChanges();

        // ── Resources count — only if assessments exist ───────────────────
        if (data.length === 0) {
          this.totalResources = 0;
          this._lastResources = 0;
          this.cdr.detectChanges();
          return;
        }

        const calls = data.slice(0, 5).map(a =>
          this.resourcesService.getByAssessment(a.id!).pipe(
            retry(1),
            catchError(() => of([]))   // on error return empty array, not null
          )
        );

        forkJoin(calls).pipe(
          catchError(() => of([]))     // if forkJoin itself fails, return empty
        ).subscribe((results: any[][]) => {
          const allResources = (results as any[][]).flat();

          // ── Only update if we got real data ──────────────────────────────
          if (allResources.length > 0 || data.length > 0) {
            this._lastResources = allResources.length;
            this.totalResources = allResources.length;
          } else {
            // Keep last known value instead of showing 0
            this.totalResources = this._lastResources;
          }

          // ── Recent activity from resources ────────────────────────────────
          const resourceActivity: ActivityItem[] = [...allResources]
            .reverse()
            .slice(0, 3)
            .map((r: any) => ({
              icon: '📁',
              message: `Resource "${r.title}" uploaded`,
              time: 'recently'
            }));

          this.recentActivity = [...assessmentActivity, ...resourceActivity].slice(0, 5);
          this.cdr.detectChanges();
        });
      });
  }

  dismissAlert(index: number): void {
    this.upcomingAlerts.splice(index, 1);
  }

  private formatRelative(dateStr: string): string {
    const diff = Date.now() - new Date(dateStr).getTime();
    const mins = Math.floor(diff / 60000);
    const hours = Math.floor(diff / 3600000);
    const days = Math.floor(diff / 86400000);
    if (mins < 1) return 'just now';
    if (mins < 60) return `${mins}min ago`;
    if (hours < 24) return `${hours}h ago`;
    return `${days}d ago`;
  }
}
