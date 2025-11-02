package com.app.bibliotecauniversitariapa.servicesimpls;

import com.app.bibliotecauniversitariapa.dtos.BookReturnDTO;
import com.app.bibliotecauniversitariapa.entities.BookReturn;
import com.app.bibliotecauniversitariapa.exceptions.ResouceNotFoundException;
import com.app.bibliotecauniversitariapa.mappers.BookReturnMapper;
import com.app.bibliotecauniversitariapa.repositories.BookReturnRepository;
import com.app.bibliotecauniversitariapa.repositories.LoanRepository;
import com.app.bibliotecauniversitariapa.services.BookReturnService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BookReturnServiceImpl implements BookReturnService {
    @Autowired
    private BookReturnRepository bookReturnRepository;
    private LoanRepository loanRepository; //hmmmm

    @Override
    public BookReturnDTO createBookReturn(BookReturnDTO bookReturnDTO) {

        if (bookReturnDTO.getLoanId() == null) {
            throw new IllegalArgumentException("El ID del préstamo (loanId) es obligatorio");
        }

        var loan = loanRepository.findById(bookReturnDTO.getLoanId())
                .orElseThrow(() -> new ResouceNotFoundException("Loan not found with id " + bookReturnDTO.getLoanId()));

        BookReturn bookReturn = BookReturnMapper.mapBookReturnDTOToBookReturn(bookReturnDTO);

        bookReturn.setLoan(loan);
        loan.setBookReturn(bookReturn);

        BookReturn savedBookReturn = bookReturnRepository.save(bookReturn);
        return BookReturnMapper.mapBookReturnToBookDTO(savedBookReturn);
    }

    @Override
    public BookReturnDTO updateBookReturn(Long bookReturnId, BookReturnDTO bookReturnDTO) {
        BookReturn bookReturn = bookReturnRepository.findById(bookReturnId).orElseThrow(
                ()-> new ResouceNotFoundException("Book Return not found with id " + bookReturnId)
        );
        bookReturn.setReturnDate(bookReturnDTO.getReturnDate());
        bookReturn.setPenaltyAmount(bookReturnDTO.getPenaltyAmount());
        bookReturn.setReason(bookReturnDTO.getReason());

        if (bookReturnDTO.getLoanId() != null &&
                (bookReturn.getLoan() == null || !bookReturn.getLoan().getId().equals(bookReturnDTO.getLoanId()))) {

            var newLoan = loanRepository.findById(bookReturnDTO.getLoanId())
                    .orElseThrow(() -> new ResouceNotFoundException("Loan not found with id " + bookReturnDTO.getLoanId()));
            if (bookReturn.getLoan() != null) {
                bookReturn.getLoan().setBookReturn(null);
            }
            bookReturn.setLoan(newLoan);
            newLoan.setBookReturn(bookReturn);
        }


        BookReturn updatedBookReturn = bookReturnRepository.save(bookReturn);
        return BookReturnMapper.mapBookReturnToBookDTO(updatedBookReturn);
    }

    @Override
    public void deleteBookReturn(Long bookReturnId) {
        BookReturn bookReturn = bookReturnRepository.findById(bookReturnId).orElseThrow(
                ()-> new ResouceNotFoundException("Book Return not found with id " + bookReturnId)
        );
        bookReturnRepository.delete(bookReturn);
    }

    @Override
    public BookReturnDTO getBookReturnById(Long bookReturnId) {
        BookReturn bookReturn = bookReturnRepository.findById(bookReturnId).orElseThrow(
                ()-> new ResouceNotFoundException("Book Return not found with id " + bookReturnId)
        );
        return BookReturnMapper.mapBookReturnToBookDTO(bookReturn);
    }

    @Override
    public List<BookReturnDTO> getBookReturns() {
        List<BookReturn> bookReturns = bookReturnRepository.findAll();
        return bookReturns.stream().map((BookReturnMapper::mapBookReturnToBookDTO)).collect(Collectors.toList());
    }
}
