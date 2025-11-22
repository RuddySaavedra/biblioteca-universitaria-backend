package com.app.bibliotecauniversitariapa.servicesimpls;

import com.app.bibliotecauniversitariapa.dtos.BookDTO;
import com.app.bibliotecauniversitariapa.entities.Author;
import com.app.bibliotecauniversitariapa.entities.Book;
import com.app.bibliotecauniversitariapa.entities.BookCopy;
import com.app.bibliotecauniversitariapa.exceptions.ResourceNotFoundException;
import com.app.bibliotecauniversitariapa.mappers.BookMapper;
import com.app.bibliotecauniversitariapa.repositories.AuthorRepository;
import com.app.bibliotecauniversitariapa.repositories.BookCopyRepository;
import com.app.bibliotecauniversitariapa.repositories.BookRepository;
import com.app.bibliotecauniversitariapa.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BookServiceImpl implements BookService {
    @Autowired
    private final BookRepository bookRepository;
    @Autowired
    private final AuthorRepository authorRepository;
    @Autowired
    private BookCopyRepository bookCopyRepository;

    @Override
    public BookDTO createBook(BookDTO bookDTO) {
        Book book = BookMapper.mapBookDTOToBook(bookDTO);

        // Verify author's existence
        Author author = verifyAuthor(bookDTO.getAuthorId());
        // Set the author to the book
        author.addBook(book);

        if (bookDTO.getCopyId() != null) {
            BookCopy bookCopy = bookCopyRepository.findById(bookDTO.getCopyId())
                    .orElseThrow(() -> new ResourceNotFoundException("Book Copy not found with id " + bookDTO.getCopyId()));

            if (bookCopy.getBook() != null) {
                throw new IllegalStateException("Book Copy with id " + bookCopy.getId() + " is already assigned to another Book.");
            }

            // Asignar y guardar author (owner) para que se escriba el fk address_id
            book.setBookCopy(bookCopy); // setAddress() también hará address.setAuthor(this)
        }

        Book savedBook = bookRepository.save(book);
        return BookMapper.mapBookToBookDTO(savedBook);
    }

    @Override
    public BookDTO updateBook(Long bookId, BookDTO bookDTO) {
        Book book = verifyBook(bookId);

        book.setTitle(bookDTO.getTitle());
        book.setSubject(bookDTO.getSubject());
        book.setIsbn(bookDTO.getIsbn());
        book.setPublicationYear(bookDTO.getPublicationYear());

        if (bookDTO.getAuthorId() != null) {
            Author newAuthor = verifyAuthor(bookDTO.getAuthorId());
            Author currentAuthor = book.getAuthor();

            if (currentAuthor == null || !currentAuthor.getId().equals(newAuthor.getId())) {
                // 1) Añadir al nuevo autor (sin quitar aún del viejo)
                book.setAuthor(newAuthor);
            }
        }

        if (bookDTO.getCopyId() == null) {
            book.setBookCopy(null);
        } else {
            BookCopy newBookCopy = bookCopyRepository.findById(bookDTO.getCopyId())
                    .orElseThrow(() -> new ResourceNotFoundException("Book Copy not found with id " + bookDTO.getCopyId()));
            Book prev = newBookCopy.getBook();
            if (prev != null && !prev.getId().equals(bookId)) {
                prev.setBookCopy(null);
            }

            book.setBookCopy(newBookCopy);
        }

        Book updatedBook = bookRepository.save(book);
        return BookMapper.mapBookToBookDTO(updatedBook);
    }

    @Override
    public void deleteBook(Long bookId) {
        Book book = verifyBook(bookId);

        // Limpiar relación bidireccional antes de borrar
        if (book.getBookCopy() != null) {
            BookCopy bookCopy = book.getBookCopy();
            bookCopy.setBook(null);
            bookCopyRepository.save(bookCopy);
        }

        bookRepository.delete(book);
    }

    @Override
    public BookDTO getBookById(Long bookId) {
        Book book = verifyBook(bookId);
        return BookMapper.mapBookToBookDTO(book);
    }

    @Override
    public List<BookDTO> getBooks() {
        return bookRepository.findAll()
                .stream()
                .map(BookMapper::mapBookToBookDTO)
                .collect(Collectors.toList());
    }

    // ===============================================================
    // 🔹 Helpers: verifyBook and verifyAuthor
    // ===============================================================
    private Book verifyBook(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id " + bookId));
    }

    private Author verifyAuthor(Long authorId) {
        return authorRepository.findById(authorId)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id " + authorId));
    }
}
