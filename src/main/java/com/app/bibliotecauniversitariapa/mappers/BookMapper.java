package com.app.bibliotecauniversitariapa.mappers;

import com.app.bibliotecauniversitariapa.dtos.BookDTO;
import com.app.bibliotecauniversitariapa.dtos.LoanDTO;
import com.app.bibliotecauniversitariapa.entities.Book;
import com.app.bibliotecauniversitariapa.entities.Loan;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class BookMapper {
    // Convertir de una clase original a un DTO
    public static BookDTO mapBookToBookDTO(Book book) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setIsbn(book.getIsbn());
        bookDTO.setPublicationYear(book.getPublicationYear());
        bookDTO.setTitle(book.getTitle());
        List<LoanDTO> loansDTO=null;
        if(book.getLoans()!=null){
            loansDTO=book.getLoans()//obtenemos los pedidos y los transformamos a DTO
                    .stream()
                    .map(LoanMapper::mapLoanToLoanDTO)
                    .collect(Collectors.toList());
        }
        bookDTO.setLoans(loansDTO); //seteamos los pedidos antes de retornar
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
