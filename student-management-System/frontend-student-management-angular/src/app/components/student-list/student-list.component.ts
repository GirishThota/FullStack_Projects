import { Component, OnInit } from '@angular/core';
import { Student } from '../../models/student';
import { StudentService } from '../../services/student.service';

@Component({
  selector: 'app-student-list',
  templateUrl: './student-list.component.html'
})
export class StudentListComponent implements OnInit {
  students: Student[] = [];
  loading = false;
  error = '';

  constructor(private studentService: StudentService) {}

  ngOnInit(): void {
    this.loadStudents();
  }

  loadStudents(): void {
    this.loading = true;
    this.error = '';
    this.studentService.getStudents().subscribe({
      next: (data) => { this.students = data || []; this.loading = false; },
      error: () => { this.error = 'Failed to load students'; this.loading = false; }
    });
  }

  deleteStudent(id: number): void {
    this.studentService.deleteStudent(id).subscribe({
      next: () => this.loadStudents(),
      error: () => this.error = 'Failed to delete student'
    });
  }
}
