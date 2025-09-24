package com.app.bibliotecauniversitariapa.mappers;

import com.app.bibliotecauniversitariapa.dtos.StudentDTO;
import com.app.bibliotecauniversitariapa.entities.Student;

public class StudentMapper {

    // Convertir de Entity a DTO
    public static StudentDTO mapStudentToStudentDTO(Student student) {
        return new StudentDTO(
                student.getId(),
                student.getName(),
                student.getEmail(),
                student.getEnrollmentNumber(),
                student.getCareer(),
                student.getSemester(),
                student.getPhone()
        );
    }

    // Convertir de DTO a Entity
    public static Student mapStudentDTOToStudent(StudentDTO studentDTO) {
        Student student = new Student();
        student.setId(studentDTO.getId());
        student.setName(studentDTO.getName());
        student.setEmail(studentDTO.getEmail());
        student.setEnrollmentNumber(studentDTO.getEnrollmentNumber());
        student.setCareer(studentDTO.getCareer());
        student.setSemester(studentDTO.getSemester());
        student.setPhone(studentDTO.getPhone());
        return student;
    }
}
