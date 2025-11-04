// java
package com.app.bibliotecauniversitariapa.mappers;

import com.app.bibliotecauniversitariapa.dtos.LoanDTO;
import com.app.bibliotecauniversitariapa.entities.Loan;

public class LoanMapper {
    public static LoanDTO mapLoanToLoanDTO(Loan loan) {
        LoanDTO loanDTO = new LoanDTO();
        loanDTO.setId(loan.getId());
        loanDTO.setLoanDate(loan.getLoanDate());
        loanDTO.setDueDate(loan.getDueDate());
        loanDTO.setStatus(loan.getStatus());
        if (loan.getBook() != null) {
            loanDTO.setBookId(loan.getBook().getId());
            loanDTO.setBookTitle(loan.getBook().getTitle());
        }
        if (loan.getBookReturn() != null) {
            loanDTO.setReturnId(loan.getBookReturn().getId());
        }
        if (loan.getStudent() != null) {
            loanDTO.setStudentId(loan.getStudent().getId());
            loanDTO.setStudentName(loan.getStudent().getName());
        }
        return loanDTO;
    }

    public static Loan mapLoanDTOToLoan(LoanDTO loanDTO) {
        if (loanDTO == null) return null;
        Loan loan = new Loan();
        loan.setId(loanDTO.getId());
        loan.setLoanDate(loanDTO.getLoanDate());
        loan.setDueDate(loanDTO.getDueDate());
        // NO asignar status desde DTO: lo controla el backend
        return loan;
    }
}
