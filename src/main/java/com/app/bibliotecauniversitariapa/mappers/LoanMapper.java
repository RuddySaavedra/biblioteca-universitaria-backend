package com.app.bibliotecauniversitariapa.mappers;

import com.app.bibliotecauniversitariapa.dtos.LoanDTO;
import com.app.bibliotecauniversitariapa.entities.Book;
import com.app.bibliotecauniversitariapa.entities.Loan;

public class LoanMapper {
    // Convertir de una clase original a un DTO
    public static LoanDTO mapLoanToLoanDTO(Loan loan) {
       LoanDTO loanDTO = new LoanDTO();
       loanDTO.setId(loan.getId());
       loanDTO.setLoanDate(loan.getLoanDate());
       loanDTO.setDueDate(loan.getDueDate());
       loanDTO.setStatus(loan.getStatus());
       Book book=loan.getBook(); //obtenemos el book de loan para obtener ID y TITLE
       if(book!=null){
           loanDTO.setBookId(book.getId());
           loanDTO.setBookName(book.getTitle());
       }
       if(loan.getBookReturn()!=null){
           loanDTO.setReturnId(loan.getBookReturn().getId());
       }
       return loanDTO;
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
