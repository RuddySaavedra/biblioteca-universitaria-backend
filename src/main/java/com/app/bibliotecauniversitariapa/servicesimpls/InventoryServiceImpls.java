package com.app.bibliotecauniversitariapa.servicesimpls;

import com.app.bibliotecauniversitariapa.dtos.InventoryDTO;
import com.app.bibliotecauniversitariapa.entities.Inventory;
import com.app.bibliotecauniversitariapa.exceptions.ResouceNotFoundException;
import com.app.bibliotecauniversitariapa.mappers.InventoryMapper;
import com.app.bibliotecauniversitariapa.repositories.InventoryRepository;
import com.app.bibliotecauniversitariapa.services.InventoryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor

public class InventoryServiceImpls implements InventoryService{
    @Autowired
    private InventoryRepository inventoryRepository;

    @Override
    public InventoryDTO createInventory(InventoryDTO inventoryDTO) {
        Inventory inventory = InventoryMapper.mapInventoryDTOToInventory(inventoryDTO);
        Inventory savedInventory = inventoryRepository.save(inventory);
        return InventoryMapper.mapInventoryToInventoryDTO(savedInventory);
    }

    @Override
    public InventoryDTO updateInventory(Long inventoryId, InventoryDTO inventoryDTO) {
        Inventory inventory = inventoryRepository.findById(inventoryId).orElseThrow(
                ()-> new ResouceNotFoundException("Inventory not found with id " + inventoryId)
        );
        inventory.setTotalCopies(inventoryDTO.getTotalCopies());
        inventory.setAvailableCopies(inventoryDTO.getAvailableCopies());
        inventory.setMinThreshold(inventoryDTO.getMinThreshold());

        Inventory updatedInventory = inventoryRepository.save(inventory);
        return InventoryMapper.mapInventoryToInventoryDTO(updatedInventory);
    }

    @Override
    public void deleteInventory(Long inventoryId) {
        Inventory inventory = inventoryRepository.findById(inventoryId).orElseThrow(
                ()-> new ResouceNotFoundException("Inventory not found with id " + inventoryId)
        );
        inventoryRepository.delete(inventory);
    }

    @Override
    public InventoryDTO getInventoryById(Long inventoryId) {
        Inventory inventory = inventoryRepository.findById(inventoryId).orElseThrow(
                ()-> new ResouceNotFoundException("Inventory not found with id " + inventoryId)
        );
        return InventoryMapper.mapInventoryToInventoryDTO(inventory);
    }

    public List<InventoryDTO> getInventories() {
        List<Inventory> inventories = inventoryRepository.findAll();
        return inventories.stream().map((InventoryMapper::mapInventoryToInventoryDTO)).collect(Collectors.toList());
    }
}
