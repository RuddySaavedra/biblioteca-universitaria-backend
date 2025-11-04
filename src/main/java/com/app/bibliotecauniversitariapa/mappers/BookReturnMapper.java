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
        dto.setPenaltyAmount(bookReturn.getPenaltyAmount());

        // incluir id del Loan si existe (solo el id, el servicio se encargará de enlazar)
        if (bookReturn.getLoan() != null) {
            dto.setLoanId(bookReturn.getLoan().getId());
        }

        return dto;
    }

    // Convertir de un DTO a una clase original
    public static BookReturn mapBookReturnDTOToBookReturn(BookReturnDTO bookReturnDTO) {
        if (bookReturnDTO == null) return null;

        BookReturn e =  new BookReturn();
        e.setId(bookReturnDTO.getId());
        e.setReturnDate(bookReturnDTO.getReturnDate());
        e.setPenaltyAmount(bookReturnDTO.getPenaltyAmount());
        // NO asignar el Loan aquí; las relaciones deben manejarse en el ServiceImpl
        return e;
    }
}
