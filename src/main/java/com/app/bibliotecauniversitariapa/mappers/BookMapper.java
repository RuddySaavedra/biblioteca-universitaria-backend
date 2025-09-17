package com.app.bibliotecauniversitariapa.mappers;

import com.app.bibliotecauniversitariapa.dtos.BookDTO;
import com.app.bibliotecauniversitariapa.entities.Book;

public class BookMapper {
    // Convertir de una clase original a un DTO
    public static BookDTO mapBookToBookDTO(Book book) {
        return new BookDTO(
                book.getId(),
                book.getTitle(),
                book.getSubject(),
                book.getIsbn(),
                book.getPublicationYear()
        );
    }
    // Convertir de un DTO a una clase original
    public static Book mapBookDTOToBook(BookDTO bookDTO) {
        Book book = new Book();
        book.setId(bookDTO.getId());
        book.setTitle(bookDTO.getTitle());
        book.setSubject(bookDTO.getSubject());
        book.setIsbn(bookDTO.getIsbn());
        book.setPublicationYear(bookDTO.getPublicationYear());
        return book;
    }
}
