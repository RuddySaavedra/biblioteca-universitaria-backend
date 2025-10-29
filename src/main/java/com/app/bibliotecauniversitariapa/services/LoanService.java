package com.app.bibliotecauniversitariapa.services;

import com.app.bibliotecauniversitariapa.dtos.LoanDTO;

import java.util.List;

public interface LoanService {
    LoanDTO createLoan(LoanDTO loanDTO);
    LoanDTO updateLoan(Long loanId, LoanDTO loanDTO);
    void deleteLoan(Long loanId);
    LoanDTO getLoanById(Long loanId);
    List<LoanDTO> getLoans();
}
