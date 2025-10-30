package com.app.bibliotecauniversitariapa.servicesimpls;

import com.app.bibliotecauniversitariapa.dtos.BookDTO;
import com.app.bibliotecauniversitariapa.entities.Author;
import com.app.bibliotecauniversitariapa.entities.Book;
import com.app.bibliotecauniversitariapa.entities.Inventory;
import com.app.bibliotecauniversitariapa.exceptions.ResouceNotFoundException;
import com.app.bibliotecauniversitariapa.mappers.BookMapper;
import com.app.bibliotecauniversitariapa.repositories.AuthorRepository;
import com.app.bibliotecauniversitariapa.repositories.BookRepository;
import com.app.bibliotecauniversitariapa.repositories.InventoryRepository;
import com.app.bibliotecauniversitariapa.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BookServiceImpl implements BookService {

    // Repositorios inyectados por constructor (gracias a @RequiredArgsConstructor).
    // Se usan para acceder a la persistencia de Book, Author e Inventory.
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final InventoryRepository inventoryRepository;

    // ===============================================================
    // 🔹 List all books (para /api/books)
    // ===============================================================
    @Override
    @Transactional(readOnly = true)
    public List<BookDTO> listAll() {
        // Se utiliza una query que trae los libros con sus autores (findAllWithAuthor)
        // Para cada entidad Book se mappea a BookDTO usando BookMapper.
        return bookRepository.findAllWithAuthor()
                .stream()
                .map(BookMapper::mapBookToBookDTO)
                .collect(Collectors.toList());
    }

    // ===============================================================
    // 🔹 List books by author (para /api/authors/{id}/books)
    // ===============================================================
    @Override
    @Transactional(readOnly = true)
    public List<BookDTO> listByEmployee(Long authorId) {
        // Si no se pasa authorId devolvemos todos los libros.
        if (authorId == null) {
            return listAll();
        }

        // Verificamos que el autor exista; si no, se lanza ResouceNotFoundException.
        verifyAuthor(authorId);

        // Filtramos por authorId y mapeamos a DTO.
        return bookRepository.findByAuthorId(authorId)
                .stream()
                .map(BookMapper::mapBookToBookDTO)
                .collect(Collectors.toList());
    }

    // ===============================================================
    // 🔹 Add book to author (para /api/authors/{id}/books)
    // ===============================================================
    @Override
    public BookDTO addToEmployee(Long authorId, BookDTO bookDTO) {
        // Verificar existencia del autor (lanza excepción si no existe)
        Author author = verifyAuthor(authorId);

        // Mapear DTO a entidad Book para persistir
        Book book = BookMapper.mapBookDTOToBook(bookDTO);

        // Validación de unicidad de ISBN: si llega un ISBN y ya existe, se lanza IllegalArgumentException
        if (bookDTO.getIsbn() != null && bookRepository.existsByIsbn(bookDTO.getIsbn())) {
            throw new IllegalArgumentException("ISBN ya existe para otro libro.");
        }

        // Vincular inventario: buscamos Inventory por id (si no existe, lanzamos ResouceNotFoundException)
        Inventory inventory = inventoryRepository.findById(bookDTO.getInventoryId())
                .orElseThrow(() -> new ResouceNotFoundException("Inventory not found with id " + bookDTO.getInventoryId()));

        // Mantener relación bidireccional Inventory <-> Book en memoria antes de guardar
        book.setInventory(inventory);
        inventory.setBook(book);

        // Mantener consistencia del lado propietario y del lado inverso:
        // author.addBook(book) debe establecer book.setAuthor(this) internamente.
        author.addBook(book);

        // Guardar el Book (si Author tiene cascade puede que no sea necesario guardar author explícitamente)
        Book saved = bookRepository.save(book);

        // Devolver DTO mapeado desde la entidad guardada
        return BookMapper.mapBookToBookDTO(saved);
    }

    // ===============================================================
    // 🔹 Get book by ID
    // ===============================================================
    @Override
    @Transactional(readOnly = true)
    public BookDTO getById(Long bookId) {
        // Búsqueda por id con excepción si no se encuentra
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResouceNotFoundException("Book not found with id " + bookId));
        return BookMapper.mapBookToBookDTO(book);
    }

    // ===============================================================
    // 🔹 Update book (por author y book id)
    // ===============================================================
    @Override
    public BookDTO update(Long authorId, Long bookId, BookDTO bookDTO) {
        // Verificar que el autor exista y obtenerlo
        Author author = verifyAuthor(authorId);

        // Obtener el libro; si no existe, lanzar excepción
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResouceNotFoundException("Book not found with id " + bookId));

        // Comprobar que el libro pertenece realmente al autor indicado
        if (book.getAuthor() == null || !book.getAuthor().getId().equals(author.getId())) {
            throw new IllegalArgumentException("El libro no pertenece a este autor.");
        }

        // Si se cambia el ISBN, validar que el nuevo ISBN no exista en otro libro
        if (bookDTO.getIsbn() != null && !bookDTO.getIsbn().equals(book.getIsbn())) {
            if (bookRepository.existsByIsbn(bookDTO.getIsbn())) {
                throw new IllegalArgumentException("ISBN ya existe para otro libro.");
            }
            book.setIsbn(bookDTO.getIsbn());
        }

        // Actualizar otros campos permitidos desde el DTO
        book.setTitle(bookDTO.getTitle());
        book.setSubject(bookDTO.getSubject());
        book.setPublicationYear(bookDTO.getPublicationYear());
        // ... otros setters si aplica

        // Guardar cambios y devolver DTO actualizado
        Book updated = bookRepository.save(book);
        return BookMapper.mapBookToBookDTO(updated);
    }

    // ===============================================================
    // 🔹 Remove book (nested route) /api/authors/{authorId}/books/{bookId}
    // ===============================================================
    @Override
    public void remove(Long authorId, Long bookId) {
        // Verificar existencia del autor
        Author author = verifyAuthor(authorId);

        // Obtener el libro y lanzar excepción si no existe
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResouceNotFoundException("Book not found with id " + bookId));

        // Asegurar que el libro pertenezca al autor solicitado
        if (book.getAuthor() == null || !book.getAuthor().getId().equals(author.getId())) {
            throw new IllegalArgumentException("El libro no pertenece a este autor.");
        }

        // Mantener coherencia del lado inverso en memoria (remover de la colección Author.books)
        if (author.getBooks() != null) {
            author.getBooks().removeIf(b -> b.getId() != null && b.getId().equals(book.getId()));
        }

        // Eliminar el libro desde el repositorio
        bookRepository.delete(book);
    }

    // ===============================================================
    // 🔹 Delete book directamente por id (/api/books/{id})
    // ===============================================================
    @Override
    public void deleteById(Long bookId) {
        // Verificar existencia antes de eliminar para lanzar excepción controlada si no existe
        if (!bookRepository.existsById(bookId)) {
            throw new ResouceNotFoundException("Book not found with id " + bookId);
        }
        bookRepository.deleteById(bookId);
    }

    // ===============================================================
    // 🔹 Helper: verificar existencia de author
    // ===============================================================
    private Author verifyAuthor(Long authorId) {
        // Método auxiliar que centraliza la comprobación de existencia del author
        return authorRepository.findById(authorId)
                .orElseThrow(() -> new ResouceNotFoundException("Author not found with id " + authorId));
    }
}
