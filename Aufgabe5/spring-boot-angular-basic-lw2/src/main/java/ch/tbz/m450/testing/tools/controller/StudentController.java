package ch.tbz.m450.testing.tools.controller;

import ch.tbz.m450.testing.tools.repository.StudentRepository;
import ch.tbz.m450.testing.tools.repository.entities.Student;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class StudentController {

    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping("/students")
    public List<Student> getStudents() {
        return (List<Student>) studentRepository.findAll();
    }

    @PostMapping("/students")
    public Student addStudent(@RequestBody Student student) {
        return studentRepository.save(student);
    }
}
