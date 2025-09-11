package com.app.bibliotecauniversitariapa.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class InventoryDTO {
    private Long id;
    private int totalCopies;
    private int availableCopies;
    private int minThreshold;
}
