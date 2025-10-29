package com.app.bibliotecauniversitariapa.services;

import com.app.bibliotecauniversitariapa.dtos.ReturnDTO;

import java.util.List;

public interface ReturnBookService {
    ReturnDTO createReturn(ReturnDTO returnDTO);
    ReturnDTO updateReturn(ReturnDTO returnDTO);
    String deleteReturn(Long returnId);
    ReturnDTO getReturnById(Long returnId);
    List<ReturnDTO> getReturns();
}
