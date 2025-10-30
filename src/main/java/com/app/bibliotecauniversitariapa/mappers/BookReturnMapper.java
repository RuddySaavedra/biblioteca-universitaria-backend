package com.app.bibliotecauniversitariapa.mappers;

import com.app.bibliotecauniversitariapa.dtos.BookReturnDTO;
import com.app.bibliotecauniversitariapa.entities.BookReturn;

public class BookReturnMapper {
    // Convertir de una clase original a un DTO
    public static BookReturnDTO mapBookReturnToBookDTO(BookReturn bookReturn) {
        if (bookReturn == null) return null;

        BookReturnDTO dto =  new BookReturnDTO();
        dto.setId(bookReturn.getId());
        dto.setReturnDate(bookReturn.getReturnDate());
        dto.setReason(bookReturn.getReason());
        dto.setPenaltyAmount(bookReturn.getPenaltyAmount());
        dto.setLoan(bookReturn.getLoan());

        return dto;
    }

    // Convertir de un DTO a una clase original
    public static BookReturn mapBookReturnDTOToBookReturn(BookReturnDTO bookReturnDTO) {
        if (bookReturnDTO == null) return null;

        BookReturn e =  new BookReturn();
        e.setId(bookReturnDTO.getId());
        e.setReturnDate(bookReturnDTO.getReturnDate());
        e.setPenaltyAmount(bookReturnDTO.getPenaltyAmount());
        e.setLoan(bookReturnDTO.getLoan());
        return e;
    }
}
