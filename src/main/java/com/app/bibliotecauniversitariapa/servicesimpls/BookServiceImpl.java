package com.app.bibliotecauniversitariapa.servicesimpls;

import com.app.bibliotecauniversitariapa.dtos.BookDTO;
import com.app.bibliotecauniversitariapa.entities.Book;
import com.app.bibliotecauniversitariapa.exceptions.ResouceNotFoundException;
import com.app.bibliotecauniversitariapa.mappers.BookMapper;
import com.app.bibliotecauniversitariapa.repositories.BookRepository;
import com.app.bibliotecauniversitariapa.services.BookService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;

    @Override
    public BookDTO createBook(BookDTO bookDTO) {
        Book book = BookMapper.mapBookDTOToBook(bookDTO);
        Book savedBook = bookRepository.save(book);
        return BookMapper.mapBookToBookDTO(savedBook);
    }

    @Override
    public BookDTO updateBook(Long bookId, BookDTO bookDTO) {
        Book book = bookRepository.findById(bookId).orElseThrow(
                ()-> new ResouceNotFoundException("Book not found with id " + bookId)
        );
        book.setTitle(bookDTO.getTitle());
        book.setSubject(bookDTO.getSubject());
        book.setIsbn(bookDTO.getIsbn());
        book.setPublicationYear(bookDTO.getPublicationYear());

        Book updatedBook = bookRepository.save(book);
        return BookMapper.mapBookToBookDTO(updatedBook);
    }

    @Override
    public void deleteBook(Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(
                ()-> new ResouceNotFoundException("Book not found with id " + bookId)
        );
        bookRepository.delete(book);
    }

    @Override
    public BookDTO getBookById(Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(
                ()-> new ResouceNotFoundException("Book not found with id " + bookId)
        );
        return BookMapper.mapBookToBookDTO(book);
    }

    @Override
    public List<BookDTO> getBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream().map((BookMapper::mapBookToBookDTO)).collect(Collectors.toList());
    }
}
