package com.app.bibliotecauniversitariapa.services;

import com.app.bibliotecauniversitariapa.dtos.AuthorDTO;

import java.util.List;

public interface AuthorService {
    AuthorDTO createAuthor(AuthorDTO authorDTO);
    AuthorDTO updateAuthor(Long authorId, AuthorDTO authorDTO);
    void deleteAuthor(Long authorId);
    AuthorDTO getAuthorById(Long authorId);
    List<AuthorDTO> getAuthors();
}
