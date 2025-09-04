package com.app.bibliotecauniversitariapa.controllers;

import com.app.bibliotecauniversitariapa.dtos.StudentDTO;
import com.app.bibliotecauniversitariapa.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/students")
@CrossOrigin
public class StudentController {

    @Autowired
    private StudentService studentService;

    // Create student
    // POST: localhost:8080/api/students
    @PostMapping
    public ResponseEntity<StudentDTO> createStudent(@RequestBody StudentDTO studentDTO) {
        StudentDTO savedStudentDTO = studentService.createStudent(studentDTO);
        return new ResponseEntity<>(savedStudentDTO, HttpStatus.CREATED);
    }

    // Get all students
    // GET: localhost:8080/api/students
    @GetMapping
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        List<StudentDTO> studentDTOS = studentService.getStudents();
        return ResponseEntity.ok(studentDTOS);
    }

    // Get student by id
    // GET: localhost:8080/api/students/{id}
    @GetMapping("{id}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable Long id) {
        StudentDTO studentDTO = studentService.getStudentById(id);
        return ResponseEntity.ok(studentDTO);
    }

    // Update student
    // PUT: localhost:8080/api/students/{id}
    @PutMapping("{id}")
    public ResponseEntity<StudentDTO> updateStudent(@PathVariable Long id, @RequestBody StudentDTO studentDTO) {
        StudentDTO updatedStudentDTO = studentService.updateStudent(id, studentDTO);
        return ResponseEntity.ok(updatedStudentDTO);
    }

    // Delete student
    // DELETE: localhost:8080/api/students/{id}
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok("Student deleted successfully.");
    }
}
