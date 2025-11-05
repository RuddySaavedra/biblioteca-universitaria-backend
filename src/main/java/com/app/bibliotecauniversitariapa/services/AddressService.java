package com.app.bibliotecauniversitariapa.services;

import com.app.bibliotecauniversitariapa.dtos.AddressDTO;

import java.util.List;

public interface AddressService {
    AddressDTO createAddress(AddressDTO addressDTO);
    AddressDTO updateAddress(Long addressId, AddressDTO addressDTO);
    void deleteAddress(Long addressId);
    AddressDTO getAddressById(Long addressId);
    List<AddressDTO> getAllAddress();
}
