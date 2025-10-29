package com.app.bibliotecauniversitariapa.mappers;

import com.app.bibliotecauniversitariapa.dtos.LoanDTO;
import com.app.bibliotecauniversitariapa.entities.Loan;

public class LoanMapper {
    // Convertir de una clase original a un DTO
    public static LoanDTO mapLoanToLoanDTO(Loan loan) {
       return new LoanDTO(
               loan.getId(),
               loan.getLoanDate(),
               loan.getDueDate(),
               loan.getStatus()
       );
    }

    // Convertir de un DTO a una clase original
    public static Loan mapLoanDTOToLoan(LoanDTO loanDTO) {
        Loan loan = new Loan();
        loan.setId(loanDTO.getId());
        loan.setLoanDate(loanDTO.getLoanDate());
        loan.setDueDate(loanDTO.getDueDate());
        loan.setStatus(loanDTO.getStatus());
        return loan;
    }
}
