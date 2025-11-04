// java
package com.app.bibliotecauniversitariapa.servicesimpls;

import com.app.bibliotecauniversitariapa.dtos.BookReturnDTO;
import com.app.bibliotecauniversitariapa.entities.BookReturn;
import com.app.bibliotecauniversitariapa.entities.Loan;
import com.app.bibliotecauniversitariapa.entities.enums.LoanStatus;
import com.app.bibliotecauniversitariapa.exceptions.ResouceNotFoundException;
import com.app.bibliotecauniversitariapa.mappers.BookReturnMapper;
import com.app.bibliotecauniversitariapa.repositories.BookReturnRepository;
import com.app.bibliotecauniversitariapa.repositories.LoanRepository;
import com.app.bibliotecauniversitariapa.services.BookReturnService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BookReturnServiceImpl implements BookReturnService {
    @Autowired
    private BookReturnRepository bookReturnRepository;

    @Autowired
    private LoanRepository loanRepository;

    @Override
    public BookReturnDTO createBookReturn(BookReturnDTO bookReturnDTO) {
        BookReturn bookReturn = BookReturnMapper.mapBookReturnDTOToBookReturn(bookReturnDTO);

        if (bookReturnDTO.getLoanId() != null) {
            Loan loan = loanRepository.findById(bookReturnDTO.getLoanId())
                    .orElseThrow(() -> new ResouceNotFoundException("Loan not found with id " + bookReturnDTO.getLoanId()));

            if (loan.getBookReturn() != null) {
                BookReturn prev = loan.getBookReturn();
                prev.setLoan(null);
                bookReturnRepository.save(prev);
            }

            bookReturn.setLoan(loan);
            loan.setBookReturn(bookReturn);
            // recalcular y guardar loan
            recalculateLoanStatus(loan);
            loanRepository.save(loan);
        }

        BookReturn saved = bookReturnRepository.save(bookReturn);
        return BookReturnMapper.mapBookReturnToBookDTO(saved);
    }

    @Override
    public BookReturnDTO updateBookReturn(Long bookReturnId, BookReturnDTO bookReturnDTO) {
        BookReturn bookReturn = bookReturnRepository.findById(bookReturnId).orElseThrow(
                ()-> new ResouceNotFoundException("Book Return not found with id " + bookReturnId)
        );

        bookReturn.setReturnDate(bookReturnDTO.getReturnDate());
        bookReturn.setPenaltyAmount(bookReturnDTO.getPenaltyAmount());

        if (bookReturnDTO.getLoanId() != null) {
            Loan newLoan = loanRepository.findById(bookReturnDTO.getLoanId())
                    .orElseThrow(() -> new ResouceNotFoundException("Loan not found with id " + bookReturnDTO.getLoanId()));

            if (bookReturn.getLoan() != null && !bookReturn.getLoan().getId().equals(newLoan.getId())) {
                Loan previousLoan = bookReturn.getLoan();
                previousLoan.setBookReturn(null);
                recalculateLoanStatus(previousLoan);
                loanRepository.save(previousLoan);
            }

            if (newLoan.getBookReturn() != null && !newLoan.getBookReturn().getId().equals(bookReturn.getId())) {
                BookReturn prevBR = newLoan.getBookReturn();
                prevBR.setLoan(null);
                bookReturnRepository.save(prevBR);
            }

            bookReturn.setLoan(newLoan);
            newLoan.setBookReturn(bookReturn);
            recalculateLoanStatus(newLoan);
            loanRepository.save(newLoan);
        } else {
            if (bookReturn.getLoan() != null) {
                Loan prevLoan = bookReturn.getLoan();
                prevLoan.setBookReturn(null);
                recalculateLoanStatus(prevLoan);
                loanRepository.save(prevLoan);
                bookReturn.setLoan(null);
            }
        }

        BookReturn updated = bookReturnRepository.save(bookReturn);
        return BookReturnMapper.mapBookReturnToBookDTO(updated);
    }

    @Override
    public void deleteBookReturn(Long bookReturnId) {
        BookReturn bookReturn = bookReturnRepository.findById(bookReturnId).orElseThrow(
                ()-> new ResouceNotFoundException("Book Return not found with id " + bookReturnId)
        );

        if (bookReturn.getLoan() != null) {
            Loan loan = bookReturn.getLoan();
            loan.setBookReturn(null);
            recalculateLoanStatus(loan);
            loanRepository.save(loan);
            bookReturn.setLoan(null);
        }

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

    // recalcula estado del loan asociado
    private void recalculateLoanStatus(Loan loan) {
        if (loan == null) return;
        if (loan.getBookReturn() != null) {
            loan.setStatus(LoanStatus.RETURNED);
            return;
        }
        LocalDate due = loan.getDueDate();
        if (due != null && due.isBefore(LocalDate.now())) {
            loan.setStatus(LoanStatus.LATE);
        } else {
            loan.setStatus(LoanStatus.ACTIVE);
        }
    }
}
