# Student Management Backend (Spring Core + Spring MVC)

## Tech
- Spring Core DI (@Service, @Autowired)
- Spring MVC REST (@RestController)
- In-memory ArrayList (no DB)
- JSON via Jackson

## REST APIs
- GET    /students         -> list all students
- POST   /students         -> add student (JSON body)
- DELETE /students/{id}    -> delete student by id

## Build
mvn clean package

This produces: target/student-management.war

## Run
Deploy the WAR on **Tomcat 9** (Servlet 4 / javax.*).

After deployment (example):
http://localhost:8080/student-management/students
