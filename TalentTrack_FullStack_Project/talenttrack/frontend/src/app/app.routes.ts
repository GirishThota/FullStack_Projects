import { Routes } from '@angular/router';
import { LoginComponent } from './pages/login.component';
import { DashboardComponent } from './pages/dashboard.component';
import { CandidatesComponent } from './pages/candidates.component';
import { InterviewsComponent } from './pages/interviews.component';
import { authGuard } from './core/auth.guard';

export const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'dashboard' },
  { path: 'login', component: LoginComponent },
  { path: 'dashboard', component: DashboardComponent, canActivate: [authGuard] },
  { path: 'candidates', component: CandidatesComponent, canActivate: [authGuard] },
  { path: 'interviews', component: InterviewsComponent, canActivate: [authGuard] },
  { path: '**', redirectTo: 'dashboard' }
];
