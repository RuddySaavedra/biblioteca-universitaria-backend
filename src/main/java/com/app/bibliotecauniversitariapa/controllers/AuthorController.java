package com.app.bibliotecauniversitariapa.controllers;

import com.app.bibliotecauniversitariapa.dtos.AuthorDTO;
import com.app.bibliotecauniversitariapa.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/authors")
@CrossOrigin
public class AuthorController {
    @Autowired
    private AuthorService authorService;


    @PostMapping
    public ResponseEntity<?> addAuthor(@RequestBody AuthorDTO authorDTO) {
        AuthorDTO savedAuthorDTO = authorService.createAuthor(authorDTO);
        return new ResponseEntity<>(savedAuthorDTO, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<AuthorDTO>> getAllAuthors() {
        List<AuthorDTO> authorDTOS = authorService.getAuthors();
        return new ResponseEntity<>(authorDTOS, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable Long id) {
        AuthorDTO authorDTO = authorService.getAuthorById(id);
        return new ResponseEntity<>(authorDTO, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateAuthor(@PathVariable Long id, @RequestBody AuthorDTO authorDTO) {
        AuthorDTO updateAuthorDTO = authorService.updateAuthor(id, authorDTO);
        return new ResponseEntity<>(updateAuthorDTO, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
