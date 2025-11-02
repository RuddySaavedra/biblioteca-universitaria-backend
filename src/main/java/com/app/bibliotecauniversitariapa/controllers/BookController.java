package com.app.bibliotecauniversitariapa.controllers;

import com.app.bibliotecauniversitariapa.dtos.BookDTO;
import com.app.bibliotecauniversitariapa.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BookController {
    private final BookService bookService;

    // ============================================================
    // 🔹 LISTAR TODOS LOS LIBROS (GET /api/books)
    // ============================================================
    @GetMapping("/books")
    public ResponseEntity<List<BookDTO>> listAll() {
        List<BookDTO> list = bookService.listByEmployee(null); // reutiliza listByEmployee(null) como en el ejemplo del profe
        return ResponseEntity.ok(list);
    }

    // ============================================================
    // 🔹 LISTAR LIBROS DE UN AUTOR (GET /api/authors/{authorId}/books)
    // ============================================================
    @GetMapping("/authors/{authorId}/books")
    public ResponseEntity<List<BookDTO>> listByAuthor(@PathVariable Long authorId) {
        List<BookDTO> list = bookService.listByEmployee(authorId);
        return ResponseEntity.ok(list);
    }

    // ============================================================
    // 🔹 OBTENER UN LIBRO POR ID (GET /api/books/{bookId})
    // ============================================================
    @GetMapping("/books/{bookId}")
    public ResponseEntity<BookDTO> getById(@PathVariable Long bookId) {
        BookDTO dto = bookService.getById(bookId);
        return ResponseEntity.ok(dto);
    }

    // ============================================================
    // 🔹 CREAR LIBRO (POST /api/books) -> requiere authorId en el DTO
    // ============================================================
    @PostMapping("/books")
    public ResponseEntity<BookDTO> create(@RequestBody BookDTO dto) {
        if (dto.getAuthorId() == null) {
            return ResponseEntity.badRequest().build();
        }
        BookDTO created = bookService.addToEmployee(dto.getAuthorId(), dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // ============================================================
    // 🔹 CREAR LIBRO (POST /api/authors/{authorId}/books) -> ruta anidada
    // ============================================================
    @PostMapping("/authors/{authorId}/books")
    public ResponseEntity<BookDTO> createNested(@PathVariable Long authorId, @RequestBody BookDTO dto) {
        BookDTO created = bookService.addToEmployee(authorId, dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // ============================================================
    // 🔹 ACTUALIZAR LIBRO (PUT /api/books/{bookId}) -> DTO debe traer authorId
    // ============================================================
    @PutMapping("/books/{bookId}")
    public ResponseEntity<BookDTO> update(@PathVariable Long bookId, @RequestBody BookDTO dto) {
        if (dto.getAuthorId() == null) {
            return ResponseEntity.badRequest().build();
        }
        BookDTO updated = bookService.update(dto.getAuthorId(), bookId, dto);
        return ResponseEntity.ok(updated);
    }

    // ============================================================
    // 🔹 ELIMINAR LIBRO (DELETE /api/books/{bookId})
    //   -> si se pasa authorId como query param hace remove(authorId, bookId)
    //   -> si no se pasa hace deleteById(bookId)
    // ============================================================
    @DeleteMapping("/books/{bookId}")
    public ResponseEntity<String> delete(@PathVariable Long bookId, @RequestParam(required = false) Long authorId) {
        if (authorId != null) {
            bookService.remove(authorId, bookId);
        } else {
            bookService.deleteById(bookId);
        }
        return ResponseEntity.ok("Book deleted successfully.");
    }
}
