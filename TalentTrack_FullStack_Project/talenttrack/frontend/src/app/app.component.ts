import { Component } from '@angular/core';
import { RouterOutlet, RouterLink } from '@angular/router';
import { AuthService } from './core/auth.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, RouterLink],
  template: `
    <div class="topbar">
      <div class="container nav">
        <div class="brand">TalentTrack</div>
        <div *ngIf="auth.isLoggedIn()" style="display:flex;gap:10px;align-items:center;">
          <a routerLink="/dashboard">Dashboard</a>
          <a routerLink="/candidates">Candidates</a>
          <a routerLink="/interviews">Interviews</a>
          <button class="btn secondary" (click)="logout()">Logout</button>
        </div>
      </div>
    </div>

    <div class="container">
      <router-outlet />
    </div>
  `
})
export class AppComponent {
  constructor(public auth: AuthService) {}
  logout() { this.auth.logout(); }
}
