# Student Management Frontend (Angular)

## Routes
- /students -> StudentListComponent
- /add-student -> StudentAddComponent
- '' -> redirects to /students

## Features
- List students in a table (ID, Name, Course, Fee) with Delete button
- Add student using Reactive Forms with validation:
  - all fields required
  - fee must be > 5000
- Uses HttpClient to call backend REST APIs (JSON)

## Setup / Run
npm install
ng serve

Make sure backend runs at: http://localhost:8080/student-management
If your backend URL is different, update `apiUrl` in:
src/app/services/student.service.ts
