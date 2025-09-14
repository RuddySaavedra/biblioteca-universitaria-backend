package com.app.bibliotecauniversitariapa.controllers;

import com.app.bibliotecauniversitariapa.dtos.InventoryDTO;
import com.app.bibliotecauniversitariapa.services.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/inventories")
@CrossOrigin
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;

    // localhost:8080/api/employees
    @PostMapping
    public ResponseEntity<InventoryDTO> createInventory(@RequestBody InventoryDTO inventoryDTO) {
        InventoryDTO savedInventoryDTO = inventoryService.createInventory(inventoryDTO);
        return new ResponseEntity<>(savedInventoryDTO, HttpStatus.CREATED);
    }

    // localhost:8080/api/employees
    @GetMapping
    public ResponseEntity<List<InventoryDTO>> getAllInventories() {
        List<InventoryDTO> inventoryDTOS = inventoryService.getInventories();
        return ResponseEntity.ok(inventoryDTOS);
    }

    // localhost:8080/api/employees
    @GetMapping("{id}")
    public ResponseEntity<InventoryDTO> getInventoryById(@PathVariable Long id) {
        InventoryDTO inventoryDTO = inventoryService.getInventoryById(id);
        return ResponseEntity.ok(inventoryDTO);
    }

    @PutMapping("{id}")
    public ResponseEntity<InventoryDTO> updateInventory(@PathVariable Long id, @RequestBody InventoryDTO inventoryDTO) {
        InventoryDTO updatedInventoryDTO = inventoryService.updateInventory(id, inventoryDTO);
        return ResponseEntity.ok(updatedInventoryDTO);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteInventory(@PathVariable Long id) {
        inventoryService.deleteInventory(id);
        return ResponseEntity.ok("Employee deleted successfully.");
    }
}
