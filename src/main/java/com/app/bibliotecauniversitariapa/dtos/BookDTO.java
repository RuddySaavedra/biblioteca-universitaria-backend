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
public class BookDTO {
    private Long id;
    private String title;
    private String subject;
    private String isbn;
    private int publicationYear;
    private List<LoanDTO> loans;

    // Datos del autor asociado
    private Long authorId;
    private String authorFullName;
}