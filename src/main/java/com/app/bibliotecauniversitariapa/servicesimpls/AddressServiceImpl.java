// java
package com.app.bibliotecauniversitariapa.servicesimpls;

import com.app.bibliotecauniversitariapa.dtos.AddressDTO;
import com.app.bibliotecauniversitariapa.entities.Address;
import com.app.bibliotecauniversitariapa.entities.Author;
import com.app.bibliotecauniversitariapa.exceptions.ResouceNotFoundException;
import com.app.bibliotecauniversitariapa.mappers.AddressMapper;
import com.app.bibliotecauniversitariapa.repositories.AddressRepository;
import com.app.bibliotecauniversitariapa.repositories.AuthorRepository;
import com.app.bibliotecauniversitariapa.services.AddressService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final AuthorRepository authorRepository;

    @Override
    public AddressDTO createAddress(AddressDTO addressDTO) {
        Address address = AddressMapper.mapAddressDTOToAddress(addressDTO);
        Address saved = addressRepository.save(address);
        return AddressMapper.mapAddressToAddressDTO(saved);
    }

    @Override
    public AddressDTO updateAddress(Long addressId, AddressDTO addressDTO) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResouceNotFoundException("Address not found with id " + addressId));

        // Actualizar campos simples
        address.setCondominium(addressDTO.getCondominium());
        address.setApartment(addressDTO.getApartment());
        address.setStreet(addressDTO.getStreet());
        address.setFloor(addressDTO.getFloor());

        Address updated = addressRepository.save(address);
        return AddressMapper.mapAddressToAddressDTO(updated);
    }

    @Override
    public void deleteAddress(Long addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResouceNotFoundException("Address not found with id " + addressId));
        // Si la dirección está asociada a un author (owner), quitar la relación para evitar violación de FK
        Author author = address.getAuthor();
        if (author != null) {
            author.setAddress(null);
            authorRepository.save(author);
        }
        addressRepository.delete(address);
    }

    @Override
    public AddressDTO getAddressById(Long addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResouceNotFoundException("Address not found with id " + addressId));
        return AddressMapper.mapAddressToAddressDTO(address);
    }

    @Override
    public List<AddressDTO> getAllAddress() {
        return addressRepository.findAll()
                .stream()
                .map(AddressMapper::mapAddressToAddressDTO)
                .collect(Collectors.toList());
    }
}
