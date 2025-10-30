package com.app.bibliotecauniversitariapa.servicesimpls;

import com.app.bibliotecauniversitariapa.dtos.BookDTO;
import com.app.bibliotecauniversitariapa.entities.Author;
import com.app.bibliotecauniversitariapa.entities.Book;
import com.app.bibliotecauniversitariapa.exceptions.ResouceNotFoundException;
import com.app.bibliotecauniversitariapa.mappers.BookMapper;
import com.app.bibliotecauniversitariapa.repositories.AuthorRepository;
import com.app.bibliotecauniversitariapa.repositories.BookRepository;
import com.app.bibliotecauniversitariapa.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    // ===============================================================
    // 🔹 List all books (para /api/books)
    // ===============================================================
    @Override
    @Transactional(readOnly = true)
    public List<BookDTO> listAll() {
        return bookRepository.findAllWithAuthor()
                .stream()
                .map(BookMapper::mapBookToBookDTO)
                .collect(Collectors.toList());
    }

    // ===============================================================
    // 🔹 List books by author (para /api/authors/{id}/books)
    // ===============================================================
    @Override
    @Transactional(readOnly = true)
    public List<BookDTO> listByEmployee(Long authorId) {
        if (authorId == null) {
            return listAll();
        }

        verifyAuthor(authorId);

        return bookRepository.findByAuthorId(authorId)
                .stream()
                .map(BookMapper::mapBookToBookDTO)
                .collect(Collectors.toList());
    }

    // ===============================================================
    // 🔹 Add book to author (para /api/authors/{id}/books)
    // ===============================================================
    @Override
    public BookDTO addToEmployee(Long authorId, BookDTO bookDTO) {
        Author author = verifyAuthor(authorId);

        if (bookDTO.getIsbn() != null && bookRepository.existsByIsbn(bookDTO.getIsbn())) {
            throw new IllegalArgumentException("ISBN ya existe para otro libro.");
        }

        Book book = BookMapper.mapBookDTOToBook(bookDTO);

        // Mantener consistencia del lado propietario y del lado inverso
        author.addBook(book); // addBook() en Author ya hace book.setAuthor(this)

        // Guardamos via bookRepository (puedes guardar también authorRepository.save(author) por cascade)
        Book saved = bookRepository.save(book);
        return BookMapper.mapBookToBookDTO(saved);
    }

    // ===============================================================
    // 🔹 Get book by ID
    // ===============================================================
    @Override
    @Transactional(readOnly = true)
    public BookDTO getById(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResouceNotFoundException("Book not found with id " + bookId));
        return BookMapper.mapBookToBookDTO(book);
    }

    // ===============================================================
    // 🔹 Update book (por author y book id)
    // ===============================================================
    @Override
    public BookDTO update(Long authorId, Long bookId, BookDTO bookDTO) {
        Author author = verifyAuthor(authorId);

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResouceNotFoundException("Book not found with id " + bookId));

        if (book.getAuthor() == null || !book.getAuthor().getId().equals(author.getId())) {
            throw new IllegalArgumentException("El libro no pertenece a este autor.");
        }

        // Si cambias ISBN, validar unicidad
        if (bookDTO.getIsbn() != null && !bookDTO.getIsbn().equals(book.getIsbn())) {
            if (bookRepository.existsByIsbn(bookDTO.getIsbn())) {
                throw new IllegalArgumentException("ISBN ya existe para otro libro.");
            }
            book.setIsbn(bookDTO.getIsbn());
        }

        // Actualizar campos
        book.setTitle(bookDTO.getTitle());
        book.setSubject(bookDTO.getSubject());
        book.setPublicationYear(bookDTO.getPublicationYear());
        // ... otros setters si aplica

        Book updated = bookRepository.save(book);
        return BookMapper.mapBookToBookDTO(updated);
    }

    // ===============================================================
    // 🔹 Remove book (nested route) /api/authors/{authorId}/books/{bookId}
    // ===============================================================
    @Override
    public void remove(Long authorId, Long bookId) {
        Author author = verifyAuthor(authorId);

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResouceNotFoundException("Book not found with id " + bookId));

        if (book.getAuthor() == null || !book.getAuthor().getId().equals(author.getId())) {
            throw new IllegalArgumentException("El libro no pertenece a este autor.");
        }

        // Mantener coherencia del lado inverso en memoria (Author.books)
        if (author.getBooks() != null) {
            author.getBooks().removeIf(b -> b.getId() != null && b.getId().equals(book.getId()));
        }

        bookRepository.delete(book);
    }

    // ===============================================================
    // 🔹 Delete book directamente por id (/api/books/{id})
    // ===============================================================
    @Override
    public void deleteById(Long bookId) {
        if (!bookRepository.existsById(bookId)) {
            throw new ResouceNotFoundException("Book not found with id " + bookId);
        }
        bookRepository.deleteById(bookId);
    }

    // ===============================================================
    // 🔹 Helper: verificar existencia de author
    // ===============================================================
    private Author verifyAuthor(Long authorId) {
        return authorRepository.findById(authorId)
                .orElseThrow(() -> new ResouceNotFoundException("Author not found with id " + authorId));
    }
}
