# TalentTrack (Full Stack Project)

A production-style Hiring & Interview Management platform.

## Features
- JWT Authentication (Spring Security)
- Role-based access (ADMIN/HR/INTERVIEWER)
- Candidate management + resume upload (PDF) + skill extraction (best-effort)
- Interview scheduling
- Dashboard stats
- Docker + MySQL

## Run using Docker
```bash
docker compose up --build
```

Backend: http://localhost:8080/swagger  
Frontend: run separately (below)

## Run backend locally
```bash
cd backend
mvn spring-boot:run
```

Seeded admin:
- email: admin@talenttrack.dev
- password: admin123

## Run frontend locally
```bash
cd frontend
npm i
npm start
```

Then open: http://localhost:4200

## Notes
- Update `app.jwt.secret` in `backend/src/main/resources/application.yml` for real deployments.
- Resume parsing uses Apache PDFBox and simple keyword matching.
