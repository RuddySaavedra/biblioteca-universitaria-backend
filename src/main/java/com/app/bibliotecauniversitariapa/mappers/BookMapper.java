package com.app.bibliotecauniversitariapa.mappers;

import com.app.bibliotecauniversitariapa.dtos.BookDTO;
import com.app.bibliotecauniversitariapa.entities.Book;

public class BookMapper {
    // Convertir de una clase original a un DTO
    public static BookDTO mapBookToBookDTO(Book book) {
        if (book == null) return null;

        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(book.getId());
        bookDTO.setTitle(book.getTitle());
        bookDTO.setIsbn(book.getIsbn());
        bookDTO.setPublicationYear(book.getPublicationYear());

        // Incluye datos del author (ID y nombre)
        if (book.getAuthor() != null) {
            bookDTO.setAuthorId(book.getAuthor().getId());
            bookDTO.setAuthorFullName(
                    book.getAuthor().getFirstName() + " " + book.getAuthor().getLastName()
            );
        }

        return bookDTO;
    }

    // Convertir de un DTO a una clase original
    public static Book mapBookDTOToBook(BookDTO bookDTO) {
        if (bookDTO == null) return null;

        Book book = new Book();
        book.setId(bookDTO.getId());
        book.setTitle(bookDTO.getTitle());
        book.setSubject(bookDTO.getSubject());
        book.setIsbn(bookDTO.getIsbn());
        book.setPublicationYear(bookDTO.getPublicationYear());

        return book;
    }
}
