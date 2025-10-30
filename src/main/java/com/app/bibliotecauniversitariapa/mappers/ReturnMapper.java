package com.app.bibliotecauniversitariapa.mappers;

import com.app.bibliotecauniversitariapa.dtos.ReturnDTO;
import com.app.bibliotecauniversitariapa.entities.ReturnBook;

public class ReturnMapper {
    public static ReturnDTO mapReturnBookToReturnDTO(ReturnBook returnBook) {
        return new ReturnDTO(
                returnBook.getId(),
                returnBook.getReturnDate(),
                returnBook.getPenaltyAmount(),
                returnBook.getNotes()
        );
    }

    public static ReturnBook mapReturnDTOToReturnBook(ReturnDTO returnDTO) {
        ReturnBook returnBook = new ReturnBook();
        returnBook.setId(returnDTO.getId());
        returnBook.setReturnDate(returnDTO.getReturnDate());
        returnBook.setPenaltyAmount(returnDTO.getPenaltyAmount());
        returnBook.setNotes(returnDTO.getNotes());

        return returnBook;
    }
}
