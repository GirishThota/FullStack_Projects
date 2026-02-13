import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { API_BASE } from '../core/api';
import { qs } from '../core/http';

type Candidate = {
  id: number;
  fullName: string;
  email: string;
  phone?: string;
  status: string;
  experienceMonths?: number;
  extractedSkills?: string;
  createdAt?: string;
};

@Component({
  standalone: true,
  imports: [FormsModule],
  template: `
  <div class="card">
    <div class="row" style="align-items:center;justify-content:space-between">
      <div>
        <h2 style="margin:0">Candidates</h2>
        <div style="color:#444">Create candidates, upload resume, search.</div>
      </div>
      <button class="btn" (click)="toggleForm()">{{showForm ? 'Close' : 'Add Candidate'}}</button>
    </div>

    <div *ngIf="showForm" class="card" style="margin-top:12px">
      <div class="row">
        <div class="col">
          <label>Full name</label>
          <input class="input" [(ngModel)]="form.fullName" />
        </div>
        <div class="col">
          <label>Email</label>
          <input class="input" [(ngModel)]="form.email" />
        </div>
        <div class="col">
          <label>Phone</label>
          <input class="input" [(ngModel)]="form.phone" />
        </div>
        <div class="col">
          <label>Experience (months)</label>
          <input class="input" [(ngModel)]="form.experienceMonths" type="number" />
        </div>
      </div>
      <div style="height:12px"></div>
      <button class="btn" (click)="create()" [disabled]="creating">{{creating ? 'Saving...' : 'Save'}}</button>
      <span *ngIf="formError" style="margin-left:10px;color:#b00020">{{formError}}</span>
    </div>

    <div class="row" style="margin-top:12px">
      <div class="col">
        <input class="input" [(ngModel)]="q" placeholder="Search by name..." />
      </div>
      <div class="col">
        <select class="input" [(ngModel)]="status">
          <option value="">All Status</option>
          <option>APPLIED</option>
          <option>SHORTLISTED</option>
          <option>INTERVIEW_SCHEDULED</option>
          <option>SELECTED</option>
          <option>REJECTED</option>
        </select>
      </div>
      <div class="col" style="display:flex;gap:8px;align-items:end">
        <button class="btn secondary" (click)="load()">Search</button>
      </div>
    </div>

    <div style="height:12px"></div>
    <table class="table">
      <thead>
        <tr>
          <th>Name</th><th>Email</th><th>Status</th><th>Skills</th><th>Resume</th>
        </tr>
      </thead>
      <tbody>
        <tr *ngFor="let c of candidates">
          <td><b>{{c.fullName}}</b></td>
          <td>{{c.email}}</td>
          <td><span class="badge">{{c.status}}</span></td>
          <td style="max-width:360px">{{c.extractedSkills || '-'}}</td>
          <td>
            <input type="file" accept="application/pdf" (change)="upload($event, c)" />
          </td>
        </tr>
      </tbody>
    </table>
  </div>
  `
})
export class CandidatesComponent {
  candidates: Candidate[] = [];
  q = '';
  status = '';
  showForm = false;

  form: any = { fullName: '', email: '', phone: '', experienceMonths: 0 };
  creating = false;
  formError = '';

  constructor(private http: HttpClient) { this.load(); }

  toggleForm() { this.showForm = !this.showForm; }

  load() {
    const url = `${API_BASE}/api/candidates${qs({ q: this.q, status: this.status, page: 0, size: 20 })}`;
    this.http.get<any>(url).subscribe(res => this.candidates = res.content ?? []);
  }

  create() {
    this.creating = true; this.formError = '';
    this.http.post(`${API_BASE}/api/candidates`, this.form).subscribe({
      next: () => { this.showForm = false; this.form = { fullName: '', email: '', phone: '', experienceMonths: 0 }; this.load(); },
      error: (e) => { this.formError = e?.error?.message ?? 'Failed'; this.creating = false; },
      complete: () => this.creating = false
    });
  }

  upload(ev: any, c: Candidate) {
    const file: File | undefined = ev?.target?.files?.[0];
    if (!file) return;
    const fd = new FormData();
    fd.append('file', file);
    this.http.post(`${API_BASE}/api/candidates/${c.id}/resume`, fd).subscribe({
      next: () => this.load(),
      error: (e) => alert(e?.error?.message ?? 'Upload failed')
    });
  }
}
