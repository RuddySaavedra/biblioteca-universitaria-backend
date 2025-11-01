package com.app.bibliotecauniversitariapa.servicesimpls;

import com.app.bibliotecauniversitariapa.dtos.LoanDTO;
import com.app.bibliotecauniversitariapa.entities.Book;
import com.app.bibliotecauniversitariapa.entities.Loan;
import com.app.bibliotecauniversitariapa.exceptions.ResouceNotFoundException;
import com.app.bibliotecauniversitariapa.mappers.LoanMapper;
import com.app.bibliotecauniversitariapa.repositories.BookRepository;
import com.app.bibliotecauniversitariapa.repositories.LoanRepository;
import com.app.bibliotecauniversitariapa.services.LoanService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LoanServiceImpl implements LoanService {
    @Autowired
    private LoanRepository loanRepository;
    private BookRepository bookRepository;

    @Override
    public LoanDTO createLoan(LoanDTO loanDTO) {
        Loan loan = LoanMapper.mapLoanDTOToLoan(loanDTO);
        Book book =bookRepository.findById(loanDTO.getBook_id()).
                orElseThrow(() -> new ResouceNotFoundException("Book not found"));
        loan.setBook(book);
        Loan savedLoan = loanRepository.save(loan);
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

        Loan savedLoan = loanRepository.save(loan);
        return LoanMapper.mapLoanToLoanDTO(savedLoan);
    }

    @Override
    public void deleteLoan(Long loanId) {
        Loan loan = loanRepository.findById(loanId).orElseThrow(
                ()-> new ResouceNotFoundException("Loan not found with id " + loanId)
        );
        loanRepository.delete(loan);
    }

    @Override
    public LoanDTO getLoanById(Long loanId) {
        Loan loan = loanRepository.findById(loanId).
                orElseThrow(()-> new ResouceNotFoundException("Loan not found with id " + loanId)
        );
        return LoanMapper.mapLoanToLoanDTO(loan);
    }
    // Hola
    @Override
    public List<LoanDTO> getLoans() {
        List<Loan> loans = loanRepository.findAll();
        return loans.stream().map((LoanMapper::mapLoanToLoanDTO)).collect(Collectors.toList());
    }
}
