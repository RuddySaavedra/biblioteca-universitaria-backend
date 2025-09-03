package com.app.bibliotecauniversitariapa.servicesimpls;

import com.app.bibliotecauniversitariapa.dtos.AuthorDTO;
import com.app.bibliotecauniversitariapa.entities.Author;
import com.app.bibliotecauniversitariapa.exceptions.ResouceNotFoundException;
import com.app.bibliotecauniversitariapa.mappers.AuthorMapper;
import com.app.bibliotecauniversitariapa.repositories.AuthorRepository;
import com.app.bibliotecauniversitariapa.services.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public AuthorDTO createAuthor(AuthorDTO authorDTO){
        Author author = AuthorMapper.mapAuthorDTOToAuthorEntity(authorDTO);
        Author savedAuthor = authorRepository.save(author);
        return AuthorMapper.mapAuthorEntityToAuthorDTO(savedAuthor);
    }

    @Override
    public AuthorDTO updateAuthor(Long authorId, AuthorDTO authorDTO) {
        Author author = authorRepository.findById(authorId).orElseThrow(
        ()-> new ResouceNotFoundException("Author not found with id" +authorId)
    );
        author.setFirstName(authorDTO.getFirstName());
        author.setLastName(authorDTO.getLastName());

        Author updatedAuthor = authorRepository.save(author);
        return AuthorMapper.mapAuthorEntityToAuthorDTO(updatedAuthor);
    }

    @Override
    public void deleteAuthor(Long authorId) {
        Author author = authorRepository.findById(authorId).orElseThrow(
                ()-> new ResouceNotFoundException("Author not found with id" +authorId)
        );
        authorRepository.delete(author);
    }
    @Override
    public AuthorDTO getAuthorById(Long authorId) {
        Author author = authorRepository.findById(authorId).orElseThrow(
                ()-> new ResouceNotFoundException("Author not found with id" +authorId)
        );
        return AuthorMapper.mapAuthorEntityToAuthorDTO(author);
    }

    @Override
    public List<AuthorDTO> getAuthors(){
        List<Author> authors = authorRepository.findAll();
        return authors.stream().map((AuthorMapper::mapAuthorEntityToAuthorDTO)).collect(Collectors.toList());
    }
}
