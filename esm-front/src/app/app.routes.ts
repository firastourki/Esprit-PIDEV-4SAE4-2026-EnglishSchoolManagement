import { Routes } from '@angular/router';
import { Content } from './content/content';
import { Signin } from './pages/signin/signin';
import { Signup } from './pages/signup/signup';
import { AdminLayout } from './layout/admin-layout/admin-layout';
import { StudentLayout } from './layout/student-layout/student-layout';
import { Backoffice } from './pages/backoffice/backoffice';
import { Dashboard } from './pages/dashboard/dashboard';
import { Resources } from './pages/resources/resources';
import { AssessmentDetails } from './pages/assessment-details/assessment-details';
import { Planning } from './pages/planning/planning';
import { GradesComponent } from './pages/grades/grades';
import { LeaderboardComponent } from './pages/leaderboard/leaderboard';
import { StudentHome } from './pages/student/student-home/student-home';
import { CourseDetailComponent } from './pages/course-detail/course-detail.component';

export const routes: Routes = [
  { path: '', component: Content },
  { path: 'signin', component: Signin },
  { path: 'signup', component: Signup },
  { path: 'course/:id', component: CourseDetailComponent },
  // ── Admin ──────────────────────────────────────────────────────────────────
  {
    path: 'backoffice',
    component: AdminLayout,
    children: [
      { path: 'dashboard', component: Dashboard },
      { path: 'assessments', component: Backoffice },
      { path: 'resources', component: Resources },
      { path: 'assessment/:id', component: AssessmentDetails },
      { path: 'planning', component: Planning },
      { path: 'grades', component: GradesComponent },
      { path: 'leaderboard', component: LeaderboardComponent },
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' }
    ]
  },
  // ── Student ────────────────────────────────────────────────────────────────
  {
    path: 'student',
    component: StudentLayout,
    children: [
      { path: 'home', component: StudentHome },
      { path: '', redirectTo: 'home', pathMatch: 'full' }
    ]
  },
  { path: '**', redirectTo: '' }
];
