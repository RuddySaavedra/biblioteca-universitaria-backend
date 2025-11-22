package com.app.bibliotecauniversitariapa.mappers;

import com.app.bibliotecauniversitariapa.dtos.BookDTO;
import com.app.bibliotecauniversitariapa.dtos.LoanDTO;
import com.app.bibliotecauniversitariapa.entities.Book;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class BookMapper {
    // Convertir de una clase original a un DTO
    public static BookDTO mapBookToBookDTO(Book book) {
        if (book == null) return null;

        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(book.getId());
        bookDTO.setTitle(book.getTitle());
        bookDTO.setSubject(book.getSubject());
        bookDTO.setIsbn(book.getIsbn());
        bookDTO.setPublicationYear(book.getPublicationYear());

        if (book.getBookCopy() != null) {
            bookDTO.setCopyId(book.getBookCopy().getId());
            bookDTO.setCopyTitle(book.getBookCopy().getTitle());
        }

        // Incluye datos del author (ID y nombre)
        if (book.getAuthor() != null) {
            bookDTO.setAuthorId(book.getAuthor().getId());
            bookDTO.setAuthorFullName(
                    book.getAuthor().getFirstName() + " " + book.getAuthor().getLastName()
            );
        }

        List<LoanDTO> loansDTO = null;

        if(book.getLoans()!=null){
            loansDTO = book.getLoans()//obtenemos los pedidos y los transformamos a DTO
                    .stream()
                    .map(LoanMapper::mapLoanToLoanDTO)
                    .collect(Collectors.toList());
        }
        bookDTO.setLoans(loansDTO); //Seteamos los pedidos antes de retornar

        return bookDTO;
    }

    // Convertir de un DTO a una clase original
    public static Book mapBookDTOToBook(BookDTO bookDTO) {
        Book book = new Book();
        book.setId(bookDTO.getId());
        book.setTitle(bookDTO.getTitle());
        book.setSubject(bookDTO.getSubject());
        book.setIsbn(bookDTO.getIsbn());
        book.setPublicationYear(bookDTO.getPublicationYear());

        if(bookDTO.getLoans()!=null){
            bookDTO.getLoans().stream()
                    .filter(Objects::nonNull)
                    .map(LoanMapper::mapLoanDTOToLoan)
                    .forEach(book::addLoan);
        }

        return book;
    }
}
