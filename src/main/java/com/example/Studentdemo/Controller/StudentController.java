package com.example.Studentdemo.Controller;
import com.example.Studentdemo.model.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/students") // Create URL
public class StudentController {

    private final List<Student> students = new ArrayList<>();

    public StudentController() {
        students.add(new Student(1, "Arun", "arun@example.com"));
        students.add(new Student(2, "Priya", "priya@example.com"));
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(students);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable int id) {
        for (Student student : students) {
            if (student.getId() == id) {
                return ResponseEntity.ok(student);
            }
        }

        return ResponseEntity.notFound().build(); // HTTP 404 NOT FOUND
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(
            @RequestBody Student student) {

        students.add(student);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(student);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(
            @PathVariable int id,
            @RequestBody Student updatedStudent) {

        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId() == id) {
                updatedStudent.setId(id);
                students.set(i, updatedStudent);
                return ResponseEntity.ok(updatedStudent);
            }
        }

        return ResponseEntity.notFound().build();
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable int id) {
        boolean removed =
                students.removeIf(student -> student.getId() == id);

        if (removed) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}

