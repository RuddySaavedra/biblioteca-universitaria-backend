package com.app.bibliotecauniversitariapa.mappers;

import com.app.bibliotecauniversitariapa.dtos.AuthorDTO;
import com.app.bibliotecauniversitariapa.dtos.BookDTO;
import com.app.bibliotecauniversitariapa.entities.Author;

import java.util.List;
import java.util.Objects;

public class AuthorMapper {
    public static AuthorDTO mapAuthorToAuthorDTO(Author author) {
        if  (author == null) return null;

        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setId(author.getId());
        authorDTO.setFirstName(author.getFirstName());
        authorDTO.setLastName(author.getLastName());
        authorDTO.setAddress(author.getAddress());
        authorDTO.setType(author.getType());

        // Map Books to BookDTOs
        List<BookDTO> bookDTOs = null;
        if (author.getBooks() != null) {
            bookDTOs = author.getBooks()
                    .stream()
                    .map(BookMapper::mapBookToBookDTO)
                    .toList();
        }

        authorDTO.setBooks(bookDTOs);

        return authorDTO;
    }

    public static Author mapAuthorDTOToAuthor(AuthorDTO authorDTO) {
        if (authorDTO == null) return null;

        Author author = new Author();
        author.setId(authorDTO.getId());
        author.setFirstName(authorDTO.getFirstName());
        author.setLastName(authorDTO.getLastName());
        author.setAddress(authorDTO.getAddress());
        author.setType(authorDTO.getType());

        if (authorDTO.getBooks() != null) {
            authorDTO.getBooks().stream()
                    .filter(Objects::nonNull)
                    .map(BookMapper::mapBookDTOToBook)
                    .forEach(author::addBook);
        }

        return author;
    }
}
