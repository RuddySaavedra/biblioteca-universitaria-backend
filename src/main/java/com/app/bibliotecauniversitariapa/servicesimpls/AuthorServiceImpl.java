// java
package com.app.bibliotecauniversitariapa.servicesimpls;

import com.app.bibliotecauniversitariapa.dtos.AuthorDTO;
import com.app.bibliotecauniversitariapa.entities.Address;
import com.app.bibliotecauniversitariapa.entities.Author;
import com.app.bibliotecauniversitariapa.exceptions.ResouceNotFoundException;
import com.app.bibliotecauniversitariapa.mappers.AuthorMapper;
import com.app.bibliotecauniversitariapa.repositories.AddressRepository;
import com.app.bibliotecauniversitariapa.repositories.AuthorRepository;
import com.app.bibliotecauniversitariapa.services.AuthorService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final AddressRepository addressRepository;

    @Override
    public AuthorDTO createAuthor(AuthorDTO authorDTO){
        Author author = AuthorMapper.mapAuthorDTOToAuthor(authorDTO);

        if (authorDTO.getAddressId() != null) {
            Address address = addressRepository.findById(authorDTO.getAddressId())
                    .orElseThrow(() -> new ResouceNotFoundException("Address not found with id " + authorDTO.getAddressId()));

            if (address.getAuthor() != null) {
                throw new IllegalStateException("Address with id " + address.getId() + " is already assigned to another author.");
            }

            // Asignar y guardar author (owner) para que se escriba el fk address_id
            author.setAddress(address); // setAddress() también hará address.setAuthor(this)
        }

        Author savedAuthor = authorRepository.save(author);

        return AuthorMapper.mapAuthorToAuthorDTO(savedAuthor);
    }

    @Override
    public AuthorDTO updateAuthor(Long authorId, AuthorDTO authorDTO) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new ResouceNotFoundException("Author not found with id" + authorId));

        author.setFirstName(authorDTO.getFirstName());
        author.setLastName(authorDTO.getLastName());
        author.setType(authorDTO.getType());

        if (authorDTO.getAddressId() == null) {
            author.setAddress(null);
        } else {
            Address newAddress = addressRepository.findById(authorDTO.getAddressId())
                    .orElseThrow(() -> new ResouceNotFoundException("Address not found with id " + authorDTO.getAddressId()));

            // si la dirección está asignada a otro autor, despegarla
            Author prev = newAddress.getAuthor();
            if (prev != null && !prev.getId().equals(authorId)) {
                prev.setAddress(null);
            }

            author.setAddress(newAddress);
        }

        Author updated = authorRepository.save(author);
        return AuthorMapper.mapAuthorToAuthorDTO(updated);
    }


    @Override
    public void deleteAuthor(Long authorId) {
        Author author = authorRepository.findById(authorId).orElseThrow(
                ()-> new ResouceNotFoundException("Author not found with id" +authorId)
        );

        // Limpiar relación bidireccional antes de borrar
        if (author.getAddress() != null) {
            Address addr = author.getAddress();
            addr.setAuthor(null);
            addressRepository.save(addr);
        }

        authorRepository.delete(author);
    }

    @Override
    public AuthorDTO getAuthorById(Long authorId) {
        Author author = authorRepository.findById(authorId).orElseThrow(
                ()-> new ResouceNotFoundException("Author not found with id" +authorId)
        );
        return AuthorMapper.mapAuthorToAuthorDTO(author);
    }

    @Override
    public List<AuthorDTO> getAuthors(){
        List<Author> authors = authorRepository.findAll();
        return authors.stream().map((AuthorMapper::mapAuthorToAuthorDTO)).collect(Collectors.toList());
    }
}
