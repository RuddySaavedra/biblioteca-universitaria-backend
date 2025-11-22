package com.app.bibliotecauniversitariapa.mappers;

import com.app.bibliotecauniversitariapa.dtos.BookCopyDTO;
import com.app.bibliotecauniversitariapa.entities.BookCopy;

public class BookCopyMapper {
    public static BookCopyDTO mapBookCopyToBookCopyDTO(BookCopy bookCopy) {
        if (bookCopy == null) return null;
        BookCopyDTO bookCopyDTO = new BookCopyDTO();
        bookCopyDTO.setId(bookCopy.getId());
        bookCopyDTO.setTitle(bookCopy.getTitle());
        return bookCopyDTO;
    }

    public static BookCopy mapBookCopyDTOToBookCopy(BookCopyDTO bookCopyDTO) {
        if (bookCopyDTO == null) return null;
        BookCopy bookCopy = new BookCopy();
        bookCopy.setId(bookCopyDTO.getId());
        bookCopy.setTitle(bookCopyDTO.getTitle());
        return bookCopy;
    }
}
