package com.app.bibliotecauniversitariapa.services;

import com.app.bibliotecauniversitariapa.dtos.BookCopyDTO;

import java.util.List;

public interface BookCopyService {
    BookCopyDTO createBookCopy(BookCopyDTO bookCopyDTO);
    BookCopyDTO updateBookCopy(Long bookCopyId, BookCopyDTO bookCopyDTO);
    void deleteBookCopy(Long bookCopyId);
    BookCopyDTO getBookCopyById(Long bookCopyId);
    List<BookCopyDTO> getBookCopies();
}
