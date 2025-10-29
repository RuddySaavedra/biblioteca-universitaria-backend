package com.app.bibliotecauniversitariapa.mappers;

import com.app.bibliotecauniversitariapa.dtos.BookReturnDTO;
import com.app.bibliotecauniversitariapa.entities.BookReturn;

public class BookReturnMapper {
    // Convertir de una clase original a un DTO
    public static BookReturnDTO mapBookReturnToBookDTO(BookReturn bookReturn) {
        return new BookReturnDTO(
                bookReturn.getId(),
                bookReturn.getReturnDate(),
                bookReturn.getPenaltyAmount(),
                bookReturn.getReason()
        );
    }

    // Convertir de un DTO a una clase original
    public static BookReturn mapBookReturnDTOToBookReturn(BookReturnDTO bookReturnDTO) {
        BookReturn bookReturn = new BookReturn();
        bookReturn.setId(bookReturnDTO.getId());
        bookReturn.setReturnDate(bookReturnDTO.getReturnDate());
        bookReturn.setPenaltyAmount(bookReturnDTO.getPenaltyAmount());
        bookReturn.setReason(bookReturnDTO.getReason());
        return bookReturn;
    }
}
