import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { API_BASE } from '../core/api';

@Component({
  standalone: true,
  template: `
  <div class="row">
    <div class="col">
      <div class="card">
        <h2>Dashboard</h2>
        <p style="color:#444">Quick stats from backend.</p>
        <div class="row">
          <div class="col"><div class="card"><b>Total</b><div style="font-size:28px">{{stats?.totalCandidates ?? '-'}}</div></div></div>
          <div class="col"><div class="card"><b>Applied</b><div style="font-size:28px">{{stats?.applied ?? '-'}}</div></div></div>
          <div class="col"><div class="card"><b>Interview Scheduled</b><div style="font-size:28px">{{stats?.interviewScheduled ?? '-'}}</div></div></div>
          <div class="col"><div class="card"><b>Selected</b><div style="font-size:28px">{{stats?.selected ?? '-'}}</div></div></div>
        </div>
        <button class="btn secondary" (click)="load()">Refresh</button>
      </div>
    </div>
  </div>
  `
})
export class DashboardComponent {
  stats: any;
  constructor(private http: HttpClient) { this.load(); }
  load() {
    this.http.get(`${API_BASE}/api/dashboard/stats`).subscribe(s => this.stats = s);
  }
}
