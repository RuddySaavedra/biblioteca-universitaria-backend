package com.app.bibliotecauniversitariapa.servicesimpls;

import com.app.bibliotecauniversitariapa.dtos.InventoryDTO;
import com.app.bibliotecauniversitariapa.entities.Book;
import com.app.bibliotecauniversitariapa.entities.Inventory;
import com.app.bibliotecauniversitariapa.exceptions.ResourceNotFoundException;
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
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id " + inventoryDTO.getBookId()));

        // Evitar crear si el libro ya tiene un Inventory asignado
        if (book.getInventory() != null) {
            throw new IllegalStateException("Book with id " + book.getId() + " already has an inventory.");
        }

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
                ()-> new ResourceNotFoundException("Inventory not found with id " + inventoryId)
        );

        // Actualizar solo los campos permitidos desde el DTO
        inventory.setTotalCopies(inventoryDTO.getTotalCopies());
        inventory.setAvailableCopies(inventoryDTO.getAvailableCopies());
        inventory.setMinThreshold(inventoryDTO.getMinThreshold());

        // Si el DTO trae bookId, gestionar la reasignación correctamente
        if (inventoryDTO.getBookId() != null) {
            Book newBook = bookRepository.findById(inventoryDTO.getBookId())
                    .orElseThrow(() -> new ResourceNotFoundException("Book not found with id " + inventoryDTO.getBookId()));

            Book currentBook = inventory.getBook();

            // Si el libro nuevo ya está asignado a otro inventory distinto, bloquear
            if (newBook.getInventory() != null && !newBook.getInventory().getId().equals(inventoryId)) {
                throw new IllegalStateException("Book with id " + newBook.getId() + " is already assigned to another inventory.");
            }

            // Si realmente hay un cambio de libro, sincronizar ambas relaciones
            if (currentBook == null || !currentBook.getId().equals(newBook.getId())) {
                // quitar referencia del libro actual (si existe)
                if (currentBook != null) {
                    currentBook.setInventory(null);
                    bookRepository.save(currentBook);
                }

                // asignar nuevo libro al inventory y viceversa
                inventory.setBook(newBook);
                newBook.setInventory(inventory);
                bookRepository.save(newBook);
            }
        }

        // Guardar los cambios y devolver el DTO resultante
        Inventory updatedInventory = inventoryRepository.save(inventory);
        return InventoryMapper.mapInventoryToInventoryDTO(updatedInventory);
    }

    @Override
    public void deleteInventory(Long inventoryId) {
        // Buscar Inventory y eliminar; se usa excepción si no existe
        Inventory inventory = inventoryRepository.findById(inventoryId).orElseThrow(
                ()-> new ResourceNotFoundException("Inventory not found with id " + inventoryId)
        );

        // Limpiar la relación bidireccional antes de eliminar para evitar inconsistencias/constraints
        Book book = inventory.getBook();
        if (book != null) {
            book.setInventory(null);
            bookRepository.save(book);
        }

        inventoryRepository.delete(inventory);
    }

    @Override
    public InventoryDTO getInventoryById(Long inventoryId) {
        // Obtener por id y mapear a DTO; excepción si no existe
        Inventory inventory = inventoryRepository.findById(inventoryId).orElseThrow(
                ()-> new ResourceNotFoundException("Inventory not found with id " + inventoryId)
        );
        return InventoryMapper.mapInventoryToInventoryDTO(inventory);
    }

    public List<InventoryDTO> getInventories() {
        // Obtener todos los Inventory y mapear cada entidad a su DTO correspondiente usando streams
        List<Inventory> inventories = inventoryRepository.findAll();
        return inventories.stream().map((InventoryMapper::mapInventoryToInventoryDTO)).collect(Collectors.toList());
    }
}
