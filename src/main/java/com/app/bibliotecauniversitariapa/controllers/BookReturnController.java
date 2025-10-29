package com.app.bibliotecauniversitariapa.controllers;

import com.app.bibliotecauniversitariapa.dtos.BookReturnDTO;
import com.app.bibliotecauniversitariapa.services.BookReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/book-returns")
@CrossOrigin
public class BookReturnController {
    @Autowired
    private BookReturnService bookReturnService;

    // localhost:8080/api/book-returns
    @PostMapping
    public ResponseEntity<BookReturnDTO> createBookReturn(@RequestBody BookReturnDTO bookReturnDTO) {
        BookReturnDTO savedBookReturnDTO = bookReturnService.createBookReturn(bookReturnDTO);
        return new ResponseEntity<>(savedBookReturnDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<BookReturnDTO>> getAllBookReturns() {
        List<BookReturnDTO> bookReturnDTOS = bookReturnService.getBookReturns();
        return ResponseEntity.ok(bookReturnDTOS);
    }

    // localhost:8080/api/book-returns/id
    @GetMapping("{id}")
    public ResponseEntity<BookReturnDTO> getBookReturnById(@PathVariable Long id) {
        BookReturnDTO bookReturnDTO = bookReturnService.getBookReturnById(id);
        return ResponseEntity.ok(bookReturnDTO);
    }

    @PutMapping("{id}")
    public ResponseEntity<BookReturnDTO> updateBookReturnById(@PathVariable Long id, @RequestBody BookReturnDTO bookReturnDTO) {
        BookReturnDTO updatedBookReturnDTO = bookReturnService.updateBookReturn(id, bookReturnDTO);
        return ResponseEntity.ok(updatedBookReturnDTO);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteBookReturnById(@PathVariable Long id) {
        bookReturnService.deleteBookReturn(id);
        return ResponseEntity.ok("Book Return deleted successfully.");
    }
}
