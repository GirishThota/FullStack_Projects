import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { API_BASE } from '../core/api';
import { AuthService } from '../core/auth.service';

@Component({
  standalone: true,
  imports: [FormsModule],
  template: `
  <div class="row">
    <div class="col">
      <div class="card">
        <h2>Login</h2>
        <p style="margin-top:0;color:#444">Use seeded admin: <span class="badge">admin@talenttrack.dev</span> / <span class="badge">admin123</span></p>
        <label>Email</label>
        <input class="input" [(ngModel)]="email" placeholder="email" />
        <div style="height:10px"></div>
        <label>Password</label>
        <input class="input" [(ngModel)]="password" type="password" placeholder="password" />
        <div style="height:14px"></div>
        <button class="btn" (click)="login()" [disabled]="loading">{{loading ? 'Logging in...' : 'Login'}}</button>
        <p *ngIf="error" style="color:#b00020;margin-top:12px">{{error}}</p>
      </div>
    </div>
  </div>
  `
})
export class LoginComponent {
  email = 'admin@talenttrack.dev';
  password = 'admin123';
  loading = false;
  error = '';

  constructor(private http: HttpClient, private router: Router, private auth: AuthService) {}

  login() {
    this.loading = true;
    this.error = '';
    this.http.post<any>(`${API_BASE}/api/auth/login`, { email: this.email, password: this.password })
      .subscribe({
        next: (res) => { this.auth.saveLogin(res); this.router.navigateByUrl('/dashboard'); },
        error: (e) => { this.error = e?.error?.message ?? 'Login failed'; this.loading = false; },
        complete: () => this.loading = false
      });
  }
}
