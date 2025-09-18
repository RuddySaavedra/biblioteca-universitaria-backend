package com.app.bibliotecauniversitariapa.mappers;

import com.app.bibliotecauniversitariapa.dtos.AuthorDTO;
import com.app.bibliotecauniversitariapa.entities.Author;

public class AuthorMapper {
    public static AuthorDTO mapAuthorEntityToAuthorDTO(Author author) {
        return new AuthorDTO(
                author.getId(),
                author.getFirstName(),
                author.getLastName(),
                author.getAddress()
        );
    }
    public static Author mapAuthorDTOToAuthorEntity(AuthorDTO authorDTO) {
        Author author = new Author();
        author.setId(authorDTO.getId());
        author.setFirstName(authorDTO.getFirstName());
        author.setLastName(authorDTO.getLastName());
        author.setAddress(authorDTO.getAddress());
        return author;
    }
}
