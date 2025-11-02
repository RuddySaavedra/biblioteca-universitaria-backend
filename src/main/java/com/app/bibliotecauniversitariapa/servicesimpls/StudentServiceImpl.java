package com.app.bibliotecauniversitariapa.servicesimpls;

import com.app.bibliotecauniversitariapa.dtos.StudentDTO;
import com.app.bibliotecauniversitariapa.entities.Student;
import com.app.bibliotecauniversitariapa.exceptions.ResouceNotFoundException;
import com.app.bibliotecauniversitariapa.mappers.StudentMapper;
import com.app.bibliotecauniversitariapa.repositories.LoanRepository;
import com.app.bibliotecauniversitariapa.repositories.StudentRepository;
import com.app.bibliotecauniversitariapa.services.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;
    private LoanRepository loanRepository;

    @Override
    public StudentDTO createStudent(StudentDTO studentDTO) {
        Student student = StudentMapper.mapStudentDTOToStudent(studentDTO);
        Student savedStudent = studentRepository.save(student);
        return StudentMapper.mapStudentToStudentDTO(savedStudent);
    }
//hola maria
    @Override
    public StudentDTO updateStudent(Long studentId, StudentDTO studentDTO) {
        Student student = studentRepository.findById(studentId).orElseThrow(
                () -> new ResouceNotFoundException("Student not found with id " + studentId)
        );

        student.setName(studentDTO.getName());
        student.setEmail(studentDTO.getEmail());
        student.setEnrollmentNumber(studentDTO.getEnrollmentNumber());
        student.setCareer(studentDTO.getCareer());
        student.setSemester(studentDTO.getSemester());
        student.setPhone(studentDTO.getPhone());

        Student updatedStudent = studentRepository.save(student);
        return StudentMapper.mapStudentToStudentDTO(updatedStudent);
    }

    @Override
    public void deleteStudent(Long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(
                () -> new ResouceNotFoundException("Student not found with id " + studentId)
        );
        studentRepository.delete(student);
    }

    @Override
    public StudentDTO getStudentById(Long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(
                () -> new ResouceNotFoundException("Student not found with id " + studentId)
        );
        return StudentMapper.mapStudentToStudentDTO(student);
    }

    @Override
    public List<StudentDTO> getStudents() {
        List<Student> students = studentRepository.findAll();
        return students.stream()
                .map(StudentMapper::mapStudentToStudentDTO)
                .collect(Collectors.toList());
    }
}
