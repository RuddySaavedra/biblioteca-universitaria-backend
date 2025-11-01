package com.app.bibliotecauniversitariapa.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InventoryDTO {
    private Long id;
    private int totalCopies;
    private int availableCopies;
    private int minThreshold;

    // Datos del libro asociado
    private Long bookId;
    private String bookTitle;
}
