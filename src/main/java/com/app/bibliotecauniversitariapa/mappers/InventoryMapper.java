package com.app.bibliotecauniversitariapa.mappers;


import com.app.bibliotecauniversitariapa.dtos.InventoryDTO;
import com.app.bibliotecauniversitariapa.entities.Book;
import com.app.bibliotecauniversitariapa.entities.Inventory;

public class InventoryMapper {
    // Convertir de una clase original a un DTO
    public static InventoryDTO mapInventoryToInventoryDTO(Inventory inventory) {
        InventoryDTO inventoryDTO =new InventoryDTO();
        inventoryDTO.setId(inventory.getId());
        inventoryDTO.setTotalCopies(inventory.getTotalCopies());
        inventoryDTO.setAvailableCopies(inventory.getAvailableCopies());
        inventoryDTO.setMinThreshold(inventory.getMinThreshold());

        // Necesario porque añadimos estos campos en el InventoryDTO
        Book book = inventory.getBook();
        if (book != null) {
            inventoryDTO.setBookId(book.getId());
            inventoryDTO.setBookTitle(inventory.getBook().getTitle());
            inventoryDTO.setBookIsbn(inventory.getBook().getIsbn());
        }
        return inventoryDTO;

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
