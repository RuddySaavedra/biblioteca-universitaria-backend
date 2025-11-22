package com.app.bibliotecauniversitariapa.controllers;


import com.app.bibliotecauniversitariapa.dtos.BookCopyDTO;
import com.app.bibliotecauniversitariapa.services.BookCopyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/book-copies")
@CrossOrigin
public class BookCopyController {
    @Autowired
    private BookCopyService bookCopyService;

    // localhost:8080/api/book-copies
    @PostMapping
    public ResponseEntity<BookCopyDTO> createBookCopy(@RequestBody BookCopyDTO bookCopyDTO) {
        BookCopyDTO savedBookCopyDTO = bookCopyService.createBookCopy(bookCopyDTO);
        return new ResponseEntity<>(savedBookCopyDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<BookCopyDTO>> getAllBookCopies() {
        List<BookCopyDTO> bookCopyDTOS = bookCopyService.getBookCopies();
        return ResponseEntity.ok(bookCopyDTOS);
    }

    // localhost:8080/api/book-Copys/id
    @GetMapping("{id}")
    public ResponseEntity<BookCopyDTO> getBookCopyById(@PathVariable Long id) {
        BookCopyDTO bookCopyDTO = bookCopyService.getBookCopyById(id);
        return ResponseEntity.ok(bookCopyDTO);
    }

    @PutMapping("{id}")
    public ResponseEntity<BookCopyDTO> updateBookCopyById(@PathVariable Long id, @RequestBody BookCopyDTO bookCopyDTO) {
        BookCopyDTO updatedBookCopyDTO = bookCopyService.updateBookCopy(id, bookCopyDTO);
        return ResponseEntity.ok(updatedBookCopyDTO);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteBookCopyById(@PathVariable Long id) {
        bookCopyService.deleteBookCopy(id);
        return ResponseEntity.ok("Book Copy deleted successfully.");
    }
}
