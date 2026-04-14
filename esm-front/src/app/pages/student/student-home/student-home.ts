import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { GradeService, Grade, LeaderboardEntry } from '../../../services/grade.service';
import { AssessmentService, Assessment } from '../../../services/assessment.service';
import { catchError, of } from 'rxjs';

type ActivePanel = null | 'leaderboard' | 'grades' | 'assessments';

interface UpcomingAlert {
  title: string;
  courseName: string;
  type: string;
  startDate: string;
  hoursLeft: number;
  urgency: 'soon' | 'urgent';
}

@Component({
  selector: 'app-student-home',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './student-home.html',
  styleUrls: ['./student-home.css']
})
export class StudentHome implements OnInit {

  activePanel: ActivePanel = null;

  studentName = 'Firas Tourki';
  studentEmail = 'firas@esprit.tn';

  // ── Alerts ────────────────────────────────────────────────────────────────
  upcomingAlerts: UpcomingAlert[] = [];

  // ── Leaderboard ────────────────────────────────────────────────────────────
  leaderboard: LeaderboardEntry[] = [];
  myRank: number | null = null;
  loadingLb = false;

  // ── Grades ────────────────────────────────────────────────────────────────
  grades: Grade[] = [];
  averagePct = 0;
  loadingGrades = false;

  // ── Assessments ───────────────────────────────────────────────────────────
  assessments: Assessment[] = [];
  filterType = '';
  loadingAssessments = false;

  constructor(
    private gradeService: GradeService,
    private assessmentService: AssessmentService,
    private cdr: ChangeDetectorRef
  ) { }

  ngOnInit(): void {
    this.fetchLeaderboard();
    this.fetchGrades();
    this.fetchAssessments();
  }

  openPanel(panel: ActivePanel): void {
    this.activePanel = this.activePanel === panel ? null : panel;
    this.cdr.detectChanges();
  }

  closePanel(): void { this.activePanel = null; this.cdr.detectChanges(); }

  dismissAlert(index: number): void {
    this.upcomingAlerts.splice(index, 1);
    this.cdr.detectChanges();
  }

  // ── Leaderboard ────────────────────────────────────────────────────────────
  fetchLeaderboard(): void {
    this.loadingLb = true;
    this.gradeService.getGlobalLeaderboard()
      .pipe(catchError(() => of([])))
      .subscribe(data => {
        this.leaderboard = data;
        const me = data.find(e => e.studentEmail === this.studentEmail);
        this.myRank = me ? me.rank : null;
        this.loadingLb = false;
        this.cdr.detectChanges();
      });
  }

  // ── Grades ─────────────────────────────────────────────────────────────────
  fetchGrades(): void {
    this.loadingGrades = true;
    this.gradeService.getByStudent(this.studentEmail)
      .pipe(catchError(() => of([])))
      .subscribe(data => {
        this.grades = data;
        if (data.length > 0) {
          const avg = data.reduce((s, g) => s + (g.score / g.maxScore) * 100, 0) / data.length;
          this.averagePct = Math.round(avg * 10) / 10;
        }
        this.loadingGrades = false;
        this.cdr.detectChanges();
      });
  }

  // ── Assessments ────────────────────────────────────────────────────────────
  fetchAssessments(): void {
    this.loadingAssessments = true;
    this.assessmentService.getAll()
      .pipe(catchError(() => of([])))
      .subscribe(data => {
        this.assessments = data.filter(a => a.status === 'PUBLISHED');

        // ── Build upcoming alerts (1h → 48h) ─────────────────────────────
        const now = Date.now();
        this.upcomingAlerts = this.assessments
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
            type: a.type,
            startDate: a.startDate!,
            hoursLeft: Math.floor(hoursLeft),
            urgency: (hoursLeft <= 6 ? 'urgent' : 'soon') as 'urgent' | 'soon'
          }));

        this.loadingAssessments = false;
        this.cdr.detectChanges();
      });
  }

  get filteredAssessments(): Assessment[] {
    if (!this.filterType) return this.assessments;
    return this.assessments.filter(a => a.type === this.filterType);
  }

  // ── Helpers ────────────────────────────────────────────────────────────────
  getPercentage(g: Grade): number {
    return Math.round((g.score / g.maxScore) * 1000) / 10;
  }

  getMention(g: Grade): string {
    const p = this.getPercentage(g);
    if (p >= 90) return 'EXCELLENT';
    if (p >= 75) return 'GOOD';
    if (p >= 60) return 'AVERAGE';
    return 'FAIL';
  }

  mentionClass(mention: string): string {
    const map: Record<string, string> = {
      EXCELLENT: 'badge-excellent',
      GOOD: 'badge-good',
      AVERAGE: 'badge-average',
      FAIL: 'badge-fail'
    };
    return map[mention] || '';
  }

  barColor(pct: number): string {
    if (pct >= 75) return '#22c55e';
    if (pct >= 60) return '#f59e0b';
    return '#ef4444';
  }

  medalEmoji(rank: number): string {
    if (rank === 1) return '🥇';
    if (rank === 2) return '🥈';
    if (rank === 3) return '🥉';
    return `#${rank}`;
  }

  isMe(email: string): boolean { return email === this.studentEmail; }

  typeIcon(type: string): string {
    const map: Record<string, string> = { EXAM: '📝', QUIZ: '⚡', PROJECT: '🏗️' };
    return map[type] || '📋';
  }

  getCountdown(dateStr: string): string {
    const diff = new Date(dateStr).getTime() - Date.now();
    if (diff < 0) return 'Past';
    const days = Math.floor(diff / 86400000);
    const hours = Math.floor((diff % 86400000) / 3600000);
    if (days > 0) return `In ${days}d ${hours}h`;
    if (hours > 0) return `In ${hours}h`;
    return 'Soon';
  }

  isQuiz(a: Assessment): boolean { return a.type === 'QUIZ'; }
}
