package com.app.bibliotecauniversitariapa.controllers;

import com.app.bibliotecauniversitariapa.dtos.BookDTO;
import com.app.bibliotecauniversitariapa.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/books")
@CrossOrigin
public class BookController {
    @Autowired
    private BookService bookService;

    // localhost:8080/api/employees
    @PostMapping
    public ResponseEntity<BookDTO> createBook(@RequestBody BookDTO bookDTO) {
        BookDTO savedBookDTO = bookService.createBook(bookDTO);
        return new ResponseEntity<>(savedBookDTO, HttpStatus.CREATED);
    }

    // localhost:8080/api/employees
    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        List<BookDTO> bookDTOS = bookService.getBooks();
        return ResponseEntity.ok(bookDTOS);
    }

    // localhost:8080/api/employees/id
    @GetMapping("{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        BookDTO bookDTO = bookService.getBookById(id);
        return ResponseEntity.ok(bookDTO);
    }

    @PutMapping("{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id, @RequestBody BookDTO bookDTO) {
        BookDTO updatedBookDTO = bookService.updateBook(id, bookDTO);
        return ResponseEntity.ok(updatedBookDTO);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok("Book deleted successfully.");
    }
}
