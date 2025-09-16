package com.app.bibliotecauniversitariapa.services;

import com.app.bibliotecauniversitariapa.dtos.InventoryDTO;

import java.util.List;


public interface InventoryService {
    InventoryDTO createInventory(InventoryDTO inventoryDTO);
    InventoryDTO updateInventory(Long inventoryId, InventoryDTO inventoryDTO);
    void deleteInventory(Long inventoryId);
    InventoryDTO getInventoryById(Long inventoryId);
    List<InventoryDTO> getInventories();
}
