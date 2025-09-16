package com.app.bibliotecauniversitariapa.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StudentDTO {
    private Long id;
    private String name;
    private String email;
    private String enrollmentNumber; // matrícula
    private String career;           // carrera o programa académico
    private int semester;            // semestre actual
}
