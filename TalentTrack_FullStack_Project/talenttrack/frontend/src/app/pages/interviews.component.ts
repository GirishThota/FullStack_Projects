import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { API_BASE } from '../core/api';
import { qs } from '../core/http';

type Interview = {
  id: number;
  candidateId: number;
  candidateName: string;
  interviewerEmail: string;
  scheduledAt: string;
  status: string;
  notes?: string;
};

@Component({
  standalone: true,
  imports: [FormsModule],
  template: `
  <div class="card">
    <div class="row" style="align-items:center;justify-content:space-between">
      <div>
        <h2 style="margin:0">Interviews</h2>
        <div style="color:#444">Schedule interviews and view list.</div>
      </div>
      <button class="btn" (click)="toggleForm()">{{showForm ? 'Close' : 'Schedule Interview'}}</button>
    </div>

    <div *ngIf="showForm" class="card" style="margin-top:12px">
      <div class="row">
        <div class="col">
          <label>Candidate ID</label>
          <input class="input" type="number" [(ngModel)]="form.candidateId" />
        </div>
        <div class="col">
          <label>Interviewer Email</label>
          <input class="input" [(ngModel)]="form.interviewerEmail" />
        </div>
        <div class="col">
          <label>Scheduled At (ISO)</label>
          <input class="input" [(ngModel)]="form.scheduledAt" placeholder="2026-02-13T10:30:00Z" />
        </div>
      </div>
      <div style="height:12px"></div>
      <button class="btn" (click)="schedule()" [disabled]="creating">{{creating ? 'Saving...' : 'Save'}}</button>
      <span *ngIf="formError" style="margin-left:10px;color:#b00020">{{formError}}</span>
    </div>

    <div class="row" style="margin-top:12px">
      <div class="col">
        <input class="input" [(ngModel)]="candidateId" placeholder="Filter by candidateId (optional)" />
      </div>
      <div class="col" style="display:flex;gap:8px;align-items:end">
        <button class="btn secondary" (click)="load()">Refresh</button>
      </div>
    </div>

    <div style="height:12px"></div>
    <table class="table">
      <thead>
        <tr>
          <th>Candidate</th><th>Interviewer</th><th>Scheduled</th><th>Status</th>
        </tr>
      </thead>
      <tbody>
        <tr *ngFor="let i of interviews">
          <td><b>{{i.candidateName}}</b> (#{{i.candidateId}})</td>
          <td>{{i.interviewerEmail}}</td>
          <td>{{i.scheduledAt}}</td>
          <td><span class="badge">{{i.status}}</span></td>
        </tr>
      </tbody>
    </table>
  </div>
  `
})
export class InterviewsComponent {
  interviews: Interview[] = [];
  candidateId = '';
  showForm = false;

  form: any = { candidateId: 1, interviewerEmail: 'interviewer@company.com', scheduledAt: new Date().toISOString() };
  creating = false;
  formError = '';

  constructor(private http: HttpClient) { this.load(); }

  toggleForm() { this.showForm = !this.showForm; }

  load() {
    const cid = this.candidateId.trim();
    const url = `${API_BASE}/api/interviews${qs({ candidateId: cid, page: 0, size: 20 })}`;
    this.http.get<any>(url).subscribe(res => this.interviews = res.content ?? []);
  }

  schedule() {
    this.creating = true; this.formError = '';
    const payload = {
      candidateId: Number(this.form.candidateId),
      interviewerEmail: this.form.interviewerEmail,
      scheduledAt: this.form.scheduledAt
    };
    this.http.post(`${API_BASE}/api/interviews`, payload).subscribe({
      next: () => { this.showForm = false; this.load(); },
      error: (e) => { this.formError = e?.error?.message ?? 'Failed'; this.creating = false; },
      complete: () => this.creating = false
    });
  }
}
