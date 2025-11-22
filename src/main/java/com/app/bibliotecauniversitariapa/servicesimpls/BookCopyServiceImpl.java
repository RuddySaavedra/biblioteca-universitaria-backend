package com.app.bibliotecauniversitariapa.servicesimpls;

import com.app.bibliotecauniversitariapa.dtos.BookCopyDTO;
import com.app.bibliotecauniversitariapa.entities.Book;
import com.app.bibliotecauniversitariapa.entities.BookCopy;
import com.app.bibliotecauniversitariapa.exceptions.ResourceNotFoundException;
import com.app.bibliotecauniversitariapa.mappers.BookCopyMapper;
import com.app.bibliotecauniversitariapa.repositories.BookCopyRepository;
import com.app.bibliotecauniversitariapa.repositories.BookRepository;
import com.app.bibliotecauniversitariapa.services.BookCopyService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class BookCopyServiceImpl implements BookCopyService {
    private final BookCopyRepository bookCopyRepository;
    private final BookRepository bookRepository;


    @Override
    public BookCopyDTO createBookCopy(BookCopyDTO bookCopyDTO) {
        BookCopy bookCopy = BookCopyMapper.mapBookCopyDTOToBookCopy(bookCopyDTO);
        BookCopy bookCopySaved = bookCopyRepository.save(bookCopy);
        return BookCopyMapper.mapBookCopyToBookCopyDTO(bookCopySaved);
    }

    @Override
    public BookCopyDTO updateBookCopy(Long bookCopyId, BookCopyDTO bookCopyDTO) {
        BookCopy bookCopy = bookCopyRepository.findById(bookCopyId)
                .orElseThrow(()->new ResourceNotFoundException("Book Copy Not Found with id " + bookCopyId));
        bookCopy.setTitle(bookCopyDTO.getTitle());

        BookCopy bookCopyUpdated = bookCopyRepository.save(bookCopy);
        return BookCopyMapper.mapBookCopyToBookCopyDTO(bookCopyUpdated);
    }

    @Override
    public void deleteBookCopy(Long bookCopyId) {
        BookCopy bookCopy = bookCopyRepository.findById(bookCopyId)
                .orElseThrow(()->new ResourceNotFoundException("Book Copy Not Found with id " + bookCopyId));
        Book book = bookCopy.getBook();
        if (book != null) {
            book.setBookCopy(null);
            bookRepository.save(book);
        }
        bookCopyRepository.delete(bookCopy);
    }

    @Override
    public BookCopyDTO getBookCopyById(Long bookCopyId) {
        BookCopy bookCopy = bookCopyRepository.findById(bookCopyId)
                .orElseThrow(()->new ResourceNotFoundException("Book Copy Not Found with id " + bookCopyId));

        return BookCopyMapper.mapBookCopyToBookCopyDTO(bookCopy);
    }

    @Override
    public List<BookCopyDTO> getBookCopies() {
        return bookCopyRepository.findAll()
                .stream()
                .map(BookCopyMapper::mapBookCopyToBookCopyDTO)
                .toList();
    }
}
