package com.app.bibliotecauniversitariapa.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {
    private Long id;
    private String name;
    private String email;
    private String enrollmentNumber; // matrícula
    private String career;           // carrera o programa académico
    private int semester;            // semestre actual
    private String phone;

    //paso 4
    private List<LoanDTO> loans;
}
