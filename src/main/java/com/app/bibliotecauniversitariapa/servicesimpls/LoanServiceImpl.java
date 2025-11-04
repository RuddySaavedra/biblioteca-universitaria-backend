package com.app.bibliotecauniversitariapa.servicesimpls;

import com.app.bibliotecauniversitariapa.dtos.LoanDTO;
import com.app.bibliotecauniversitariapa.entities.Book;
import com.app.bibliotecauniversitariapa.entities.BookReturn;
import com.app.bibliotecauniversitariapa.entities.Loan;
import com.app.bibliotecauniversitariapa.exceptions.ResouceNotFoundException;
import com.app.bibliotecauniversitariapa.mappers.LoanMapper;
import com.app.bibliotecauniversitariapa.repositories.BookRepository;
import com.app.bibliotecauniversitariapa.repositories.BookReturnRepository;
import com.app.bibliotecauniversitariapa.repositories.LoanRepository;
import com.app.bibliotecauniversitariapa.services.LoanService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class LoanServiceImpl implements LoanService {
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookReturnRepository bookReturnRepository;

    @Override
    public LoanDTO createLoan(LoanDTO loanDTO) {
        Loan loan = LoanMapper.mapLoanDTOToLoan(loanDTO);

        Book book = verifyBook(loanDTO.getBookId());
        loan.setBook(book);
        // guardar loan primero para tener id
        Loan savedLoan = loanRepository.save(loan);
        // si llega returnId, enlazar bidireccionalmente
        if (loanDTO.getReturnId() != null) {
            BookReturn br = verifyBookReturn(loanDTO.getReturnId());
            // si el BookReturn estaba vinculado a otro loan, romper ese vínculo
            if (br.getLoan() != null && !br.getLoan().getId().equals(savedLoan.getId())) {
                Loan previous = br.getLoan();
                previous.setBookReturn(null);
                loanRepository.save(previous);
            }
            br.setLoan(savedLoan);
            savedLoan.setBookReturn(br);
            bookReturnRepository.save(br);
            savedLoan = loanRepository.save(savedLoan);
        }

        return LoanMapper.mapLoanToLoanDTO(savedLoan);
    }

    @Override
    public LoanDTO updateLoan(Long loanId, LoanDTO loanDTO) {
        Loan loan = loanRepository.findById(loanDTO.getId()).
                orElseThrow(()-> new ResouceNotFoundException("Loan not found with id " + loanId)
        );
        loan.setLoanDate(loanDTO.getLoanDate());
        loan.setDueDate(loanDTO.getDueDate());
        loan.setStatus(loanDTO.getStatus());

        // manejar relación con BookReturn según returnId en DTO
        if (loanDTO.getReturnId() != null) {
            BookReturn br = verifyBookReturn(loanDTO.getReturnId());
            // desvincular BookReturn de otro loan si aplica
            if (br.getLoan() != null && !br.getLoan().getId().equals(loan.getId())) {
                Loan previous = br.getLoan();
                previous.setBookReturn(null);
                loanRepository.save(previous);
            }
            br.setLoan(loan);
            loan.setBookReturn(br);
            bookReturnRepository.save(br);
        } else {
            // si DTO no incluye returnId, romper relación si existe
            if (loan.getBookReturn() != null) {
                BookReturn old = loan.getBookReturn();
                old.setLoan(null);
                bookReturnRepository.save(old);
                loan.setBookReturn(null);
            }
        }

        Loan savedLoan = loanRepository.save(loan);
        return LoanMapper.mapLoanToLoanDTO(savedLoan);
    }

    @Override
    public void deleteLoan(Long loanId) {
        Loan loan = verifyLoan(loanId);
        // Romper vínculo bidireccional con BookReturn (opcional pero recomendable)
        if (loan.getBookReturn() != null) {
            loan.getBookReturn().setLoan(null);
            loan.setBookReturn(null);
        }
        loanRepository.delete(loan);
    }

    @Override
    public LoanDTO getLoanById(Long loanId) {
        Loan loan = verifyLoan(loanId);
        return LoanMapper.mapLoanToLoanDTO(loan);
    }

    @Override
    public List<LoanDTO> getLoans() {
        List<Loan> loans = loanRepository.findAll();
        return loans.stream().map((LoanMapper::mapLoanToLoanDTO)).collect(Collectors.toList());
    }

    // ===============================================================
    // 🔹 Helpers: verifyLoan, verifyBook and verifyBookReturn
    // ===============================================================
    private Loan verifyLoan(Long loanId) {
        return loanRepository.findById(loanId)
                .orElseThrow(() -> new ResouceNotFoundException("Loan not found with id " + loanId));
    }

    private Book verifyBook(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new ResouceNotFoundException("Book not found with id " + bookId));
    }

    private BookReturn verifyBookReturn(Long returnId) {
        return bookReturnRepository.findById(returnId)
                .orElseThrow(() -> new ResouceNotFoundException("BookReturn not found with id " + returnId));
    }
}
