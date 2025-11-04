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

    @Override
    public BookDTO createBook(BookDTO bookDTO) {
        Book book = BookMapper.mapBookDTOToBook(bookDTO);

        // Verify author's existence
        Author author = verifyAuthor(bookDTO.getAuthorId());
        // Set the author to the book
        author.addBook(book);

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

        Book updatedBook = bookRepository.save(book);
        return BookMapper.mapBookToBookDTO(updatedBook);
    }

    @Override
    public void deleteBook(Long bookId) {
        Book book = verifyBook(bookId);
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
                .orElseThrow(() -> new ResouceNotFoundException("Book not found with id " + bookId));
    }

    private Author verifyAuthor(Long authorId) {
        return authorRepository.findById(authorId)
                .orElseThrow(() -> new ResouceNotFoundException("Author not found with id " + authorId));
    }
}
