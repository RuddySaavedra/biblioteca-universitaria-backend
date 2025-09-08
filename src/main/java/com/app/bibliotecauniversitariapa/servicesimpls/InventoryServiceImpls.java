package com.app.grupo.services.impls;

import com.app.grupo.dtos.InventoryDTO;
import com.app.grupo.entities.Inventory;
import com.app.grupo.exceptions.ResouceNotFoundException;
import com.app.grupo.mappers.InventoryMapper;
import com.app.grupo.repositories.InventoryRepository;
import com.app.grupo.services.InventoryService;
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

    @Override
    public List<InventoryDTO> getInventorys() {
        List<Inventory> inventorys = inventoryRepository.findAll();
        return inventorys.stream().map((InventoryMapper::mapInventoryToInventoryDTO)).collect(Collectors.toList());
    }
}
