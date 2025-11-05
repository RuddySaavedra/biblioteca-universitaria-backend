package com.app.bibliotecauniversitariapa.mappers;

import com.app.bibliotecauniversitariapa.dtos.AddressDTO;
import com.app.bibliotecauniversitariapa.entities.Address;

public class AddressMapper {
    public static AddressDTO mapAddressToAddressDTO(Address address) {
        if (address == null) return null;

        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setId(address.getId());
        addressDTO.setCondominium(address.getCondominium());
        addressDTO.setApartment(address.getApartment());
        addressDTO.setStreet(address.getStreet());
        addressDTO.setFloor(address.getFloor());
        return addressDTO;
    }

    public static Address mapAddressDTOToAddress(AddressDTO addressDTO) {
        if (addressDTO == null) return null;
        Address address = new Address();
        address.setId(addressDTO.getId());
        address.setCondominium(addressDTO.getCondominium());
        address.setApartment(addressDTO.getApartment());
        address.setStreet(addressDTO.getStreet());
        address.setFloor(addressDTO.getFloor());
        return address;
    }
}
