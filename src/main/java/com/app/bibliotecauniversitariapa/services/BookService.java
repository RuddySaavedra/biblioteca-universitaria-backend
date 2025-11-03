package com.app.bibliotecauniversitariapa.services;

import com.app.bibliotecauniversitariapa.dtos.BookDTO;

import java.util.List;

public interface BookService {
    BookDTO createBook(BookDTO bookDTO);
    BookDTO updateBook(Long bookId, BookDTO bookDTO);
    void deleteBook(Long bookId);
    BookDTO getBookById(Long bookId);
    List<BookDTO> getBooks();
}
