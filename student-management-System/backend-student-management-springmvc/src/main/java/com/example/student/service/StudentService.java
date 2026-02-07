package com.example.student.service;

import com.example.student.model.Student;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class StudentService {

    private final List<Student> students = new ArrayList<>();

    public StudentService() {
        // sample in-memory data (can be removed if not needed)
        students.add(new Student(1, "Akhil", "Java", 6000));
        students.add(new Student(2, "Meena", "Angular", 7500));
        students.add(new Student(3, "Rahul", "Python", 9000));
    }

    public List<Student> getAllStudents() {
        return students;
    }

    public void addStudent(Student student) {
        // replace if same id exists
        deleteStudent(student.getId());
        students.add(student);
    }

    public void deleteStudent(int id) {
        Iterator<Student> it = students.iterator();
        while (it.hasNext()) {
            Student s = it.next();
            if (s.getId() == id) {
                it.remove();
                break;
            }
        }
    }
}
