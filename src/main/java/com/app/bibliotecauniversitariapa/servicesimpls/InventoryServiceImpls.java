package com.app.bibliotecauniversitariapa.servicesimpls;

import com.app.bibliotecauniversitariapa.dtos.InventoryDTO;
import com.app.bibliotecauniversitariapa.entities.Book;
import com.app.bibliotecauniversitariapa.entities.Inventory;
import com.app.bibliotecauniversitariapa.exceptions.ResouceNotFoundException;
import com.app.bibliotecauniversitariapa.mappers.InventoryMapper;
import com.app.bibliotecauniversitariapa.repositories.BookRepository;
import com.app.bibliotecauniversitariapa.repositories.InventoryRepository;
import com.app.bibliotecauniversitariapa.services.InventoryService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class InventoryServiceImpls implements InventoryService{
    // Repositorios usados por el servicio.
    // @Autowired inyecta la dependencia; @AllArgsConstructor permite inyección por constructor si se prefiere.
    @Autowired
    private InventoryRepository inventoryRepository;
    private BookRepository bookRepository;

    @Override
    @Transactional
    public InventoryDTO createInventory(InventoryDTO inventoryDTO) {
        // Mapear DTO a entidad para persistir
        Inventory inventory = InventoryMapper.mapInventoryDTOToInventory(inventoryDTO);

        // Buscar el Book relacionado por id; si no existe, lanzar excepción controlada
        Book book = bookRepository.findById(inventoryDTO.getBookId())
                .orElseThrow(() -> new ResouceNotFoundException("Book not found with id " + inventoryDTO.getBookId()));

        // Establecer la relación bidireccional entre Inventory y Book
        inventory.setBook(book);
        book.setInventory(inventory);

        // Guardar Inventory en la base de datos; la relación con Book se mantiene
        Inventory savedInventory = inventoryRepository.save(inventory);

        // Mapear la entidad guardada de vuelta a DTO para devolver al controlador
        return InventoryMapper.mapInventoryToInventoryDTO(savedInventory);
    }

    @Override
    public InventoryDTO updateInventory(Long inventoryId, InventoryDTO inventoryDTO) {
        // Buscar Inventory existente; si no se encuentra, lanzar excepción
        Inventory inventory = inventoryRepository.findById(inventoryId).orElseThrow(
                ()-> new ResouceNotFoundException("Inventory not found with id " + inventoryId)
        );

        // Actualizar solo los campos permitidos desde el DTO
        inventory.setTotalCopies(inventoryDTO.getTotalCopies());
        inventory.setAvailableCopies(inventoryDTO.getAvailableCopies());
        inventory.setMinThreshold(inventoryDTO.getMinThreshold());

        // Guardar los cambios y devolver el DTO resultante
        Inventory updatedInventory = inventoryRepository.save(inventory);
        return InventoryMapper.mapInventoryToInventoryDTO(updatedInventory);
    }

    @Override
    public void deleteInventory(Long inventoryId) {
        // Buscar Inventory y eliminar; se usa excepción si no existe
        Inventory inventory = inventoryRepository.findById(inventoryId).orElseThrow(
                ()-> new ResouceNotFoundException("Inventory not found with id " + inventoryId)
        );
        inventoryRepository.delete(inventory);
    }

    @Override
    public InventoryDTO getInventoryById(Long inventoryId) {
        // Obtener por id y mapear a DTO; excepción si no existe
        Inventory inventory = inventoryRepository.findById(inventoryId).orElseThrow(
                ()-> new ResouceNotFoundException("Inventory not found with id " + inventoryId)
        );
        return InventoryMapper.mapInventoryToInventoryDTO(inventory);
    }

    public List<InventoryDTO> getInventories() {
        // Obtener todos los Inventory y mapear cada entidad a su DTO correspondiente usando streams
        List<Inventory> inventories = inventoryRepository.findAll();
        return inventories.stream().map((InventoryMapper::mapInventoryToInventoryDTO)).collect(Collectors.toList());
    }
}
