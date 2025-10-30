package com.app.bibliotecauniversitariapa.mappers;

import com.app.bibliotecauniversitariapa.dtos.LoanDTO;
import com.app.bibliotecauniversitariapa.entities.Book;
import com.app.bibliotecauniversitariapa.entities.Loan;
//paso 5 eliminar lo que esta adentro de cada una. anadir los atributos del entities
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
           loanDTO.setBook_id(book.getId());
           loanDTO.setBook_Name(book.getTitle());
       }

      //paso 6 incluir datos del estudiante (nombre y id)
        if (loan.getStudent() != null){
            loanDTO.setStudentId(loan.getStudent().getId());
            loanDTO.setStudentName(loan.getStudent().getName());
        }
        return loanDTO;
    }
    //paso 7 anadir validacion if(...)
    // Convertir de un DTO a una clase original
    public static Loan mapLoanDTOToLoan(LoanDTO loanDTO) {
        if (loanDTO == null) return null;
        Loan loan = new Loan();
        loan.setId(loanDTO.getId());
        loan.setLoanDate(loanDTO.getLoanDate());
        loan.setDueDate(loanDTO.getDueDate());
        loan.setStatus(loanDTO.getStatus());
        return loan;
    }
}
