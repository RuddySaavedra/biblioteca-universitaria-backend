package com.app.bibliotecauniversitariapa.mappers;


import com.app.bibliotecauniversitariapa.dtos.InventoryDTO;
import com.app.bibliotecauniversitariapa.entities.Inventory;

public class InventoryMapper {
    // Convertir de una clase original a un DTO
    public static InventoryDTO mapInventoryToInventoryDTO(Inventory inventory) {
        return new InventoryDTO(
                inventory.getId(),
                inventory.getTotalCopies(),
                inventory.getAvailableCopies(),
                inventory.getMinThreshold()
        );
    }
    // Convertir de un DTO a una clase original
    public static Inventory mapInventoryDTOToInventory(InventoryDTO inventoryDTO) {
        Inventory inventory = new Inventory();
        inventory.setId(inventoryDTO.getId());
        inventory.setTotalCopies(inventoryDTO.getTotalCopies());
        inventory.setAvailableCopies(inventoryDTO.getAvailableCopies());
        inventory.setMinThreshold(inventoryDTO.getMinThreshold());
        return inventory;
    }
}
