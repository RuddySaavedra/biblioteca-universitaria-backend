package com.app.bibliotecauniversitariapa.services;

import com.app.bibliotecauniversitariapa.dtos.BookReturnDTO;

import java.util.List;

public interface BookReturnService {
    BookReturnDTO createBookReturn(BookReturnDTO bookReturnDTO);
    BookReturnDTO updateBookReturn(Long bookReturnId, BookReturnDTO bookReturnDTO);
    void deleteBookReturn(Long bookReturnId);
    BookReturnDTO getBookReturnById(Long bookReturnId);
    List<BookReturnDTO> getBookReturns();
}
