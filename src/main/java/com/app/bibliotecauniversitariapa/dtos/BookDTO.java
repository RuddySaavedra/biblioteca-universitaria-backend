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

    // Datos del autor asociado
    private Long authorId;
    private String authorFullName;

    // Datos del BookCopy asociado
    private Long copyId;
    private String copyTitle;

    private List<LoanDTO> loans;
}