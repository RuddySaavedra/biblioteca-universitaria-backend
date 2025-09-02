package com.app.bibliotecauniversitariapa.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BookDTO {
    private Long id;
    private String title;
    private String subject;
    private String isbn;
    private int publicationYear;
}
