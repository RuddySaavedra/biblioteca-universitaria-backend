package com.app.bibliotecauniversitariapa.mappers;

import com.app.bibliotecauniversitariapa.dtos.LoanDTO;
import com.app.bibliotecauniversitariapa.dtos.StudentDTO;
import com.app.bibliotecauniversitariapa.entities.Student;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class StudentMapper {
    //paso 8 hacer lo mismo de paso 6 y 7
    // Convertir de Entity a DTO
    public static StudentDTO mapStudentToStudentDTO(Student student) {
        if (student == null) return null;
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(student.getId());
        studentDTO.setName(student.getName());
        studentDTO.setEmail(student.getEmail());
        studentDTO.setEnrollmentNumber(student.getEnrollmentNumber());
        studentDTO.setCareer(student.getCareer());
        studentDTO.setSemester(student.getSemester());
        studentDTO.setPhone(student.getPhone());

        // Paso 9: agregar la lista de préstamos (loans)
        List<LoanDTO> loanDTOs = null;
        if (student.getLoans() != null) {
            loanDTOs = student.getLoans()
                    .stream()
                    .map(LoanMapper::mapLoanToLoanDTO)
                    .collect(Collectors.toList());
        }
        studentDTO.setLoans(loanDTOs);

        return studentDTO;
    }
//Convertir de DTO a Entity
    //paso 10 if(...)
    public static Student mapStudentDTOToStudent(StudentDTO studentDTO) {
        if (studentDTO == null) return null;

        Student student = new Student();
        student.setId(studentDTO.getId());
        student.setName(studentDTO.getName());
        student.setEmail(studentDTO.getEmail());
        student.setEnrollmentNumber(studentDTO.getEnrollmentNumber());
        student.setCareer(studentDTO.getCareer());
        student.setSemester(studentDTO.getSemester());
        student.setPhone(studentDTO.getPhone());

        if (studentDTO.getLoans() != null) {
            studentDTO.getLoans().stream()
                    .filter(Objects::nonNull)
                    .map(LoanMapper::mapLoanDTOToLoan)
                    .forEach(student::addLoan);
        }

        return student;
    }



}

