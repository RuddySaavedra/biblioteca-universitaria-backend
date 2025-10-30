package com.app.bibliotecauniversitariapa.services;

import com.app.bibliotecauniversitariapa.dtos.BookDTO;

import java.util.List;

public interface BookService {
    List<BookDTO> listAll();
    List<BookDTO> listByEmployee(Long authorId);
    BookDTO addToEmployee(Long authorId, BookDTO bookDTO);
    BookDTO getById(Long bookId);
    BookDTO update(Long authorId, Long bookId, BookDTO bookDTO);
    void remove(Long authorId, Long bookId);
    void deleteById(Long bookId);
}
