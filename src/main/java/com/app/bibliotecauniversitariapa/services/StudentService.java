package com.app.bibliotecauniversitariapa.services;

import com.app.bibliotecauniversitariapa.dtos.StudentDTO;

import java.util.List;

public interface StudentService {
    StudentDTO createStudent(StudentDTO studentDTO);
    StudentDTO updateStudent(Long studentId, StudentDTO studentDTO);
    void deleteStudent(Long studentId);
    StudentDTO getStudentById(Long studentId);
    List<StudentDTO> getStudents();
}
