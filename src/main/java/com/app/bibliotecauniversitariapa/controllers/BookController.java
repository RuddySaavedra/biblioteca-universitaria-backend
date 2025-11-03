package com.app.bibliotecauniversitariapa.controllers;

import com.app.bibliotecauniversitariapa.dtos.BookDTO;
import com.app.bibliotecauniversitariapa.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController {
    @Autowired
    private final BookService bookService;

    // ============================================================
    // 🔹 CREAR LIBRO (POST /api/books) -> requiere authorId en el DTO
    // ============================================================
    @PostMapping
    public ResponseEntity<BookDTO> createBook(@RequestBody BookDTO bookDTO) {
        BookDTO savedBookDTO = bookService.createBook(bookDTO);
        return new ResponseEntity<>(savedBookDTO, HttpStatus.CREATED);
    }

    // ============================================================
    // 🔹 LISTAR TODOS LOS LIBROS (GET /api/books)
    // ============================================================
    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        List<BookDTO> bookDTOS = bookService.getBooks();
        return ResponseEntity.ok(bookDTOS);
    }

    // ============================================================
    // 🔹 OBTENER UN LIBRO POR ID (GET /api/books/{bookId})
    // ============================================================
    @GetMapping("{bookId}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long bookId) {
        BookDTO bookDTO = bookService.getBookById(bookId);
        return ResponseEntity.ok(bookDTO);
    }

    // ============================================================
    // 🔹 ACTUALIZAR LIBRO (PUT /api/books/{bookId}) -> DTO debe traer authorId
    // ============================================================
    @PutMapping("{bookId}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long bookId, @RequestBody BookDTO bookDTO) {
        BookDTO updateBookDTO = bookService.updateBook(bookId, bookDTO);
        return ResponseEntity.ok(updateBookDTO);
    }

    // ============================================================
    // 🔹 ELIMINAR LIBRO (DELETE /api/books/{bookId})
    // ============================================================
    @DeleteMapping("{bookId}")
    public ResponseEntity<String> delete(@PathVariable Long bookId) {
        bookService.deleteBook(bookId);
        return ResponseEntity.ok("Book deleted successfully.");
    }
}
