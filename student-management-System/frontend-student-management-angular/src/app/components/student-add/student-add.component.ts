import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { StudentService } from '../../services/student.service';

@Component({
  selector: 'app-student-add',
  templateUrl: './student-add.component.html'
})
export class StudentAddComponent {
  error = '';
  saving = false;

  form = this.fb.group({
    id: [null as any, [Validators.required]],
    name: ['', [Validators.required]],
    course: ['', [Validators.required]],
    fee: [null as any, [Validators.required, Validators.min(5001)]]
  });

  constructor(
    private fb: FormBuilder,
    private studentService: StudentService,
    private router: Router
  ) {}

  submit(): void {
    this.error = '';
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }
    this.saving = true;
    this.studentService.addStudent(this.form.value as any).subscribe({
      next: () => {
        this.saving = false;
        this.router.navigate(['/students']);
      },
      error: () => {
        this.saving = false;
        this.error = 'Failed to add student';
      }
    });
  }
}
