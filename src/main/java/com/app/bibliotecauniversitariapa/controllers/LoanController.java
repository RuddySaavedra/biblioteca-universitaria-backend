package com.app.bibliotecauniversitariapa.controllers;

import com.app.bibliotecauniversitariapa.dtos.LoanDTO;
import com.app.bibliotecauniversitariapa.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/loans")
@CrossOrigin
public class LoanController {
    @Autowired
    private LoanService loanService;

    // localhost:8080/api/loans
    @PostMapping
    public ResponseEntity<LoanDTO> createLoan(@RequestBody LoanDTO loanDTO) {
        LoanDTO savedLoanDTO = loanService.createLoan(loanDTO);
        return new ResponseEntity<>(savedLoanDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<LoanDTO>> getAllLoans() {
        List<LoanDTO> loanDTOS = loanService.getLoans();
        return ResponseEntity.ok(loanDTOS);
    }

    @GetMapping("{id}")
    public ResponseEntity<LoanDTO> getLoanById(@PathVariable Long id) {
        LoanDTO loanDTO = loanService.getLoanById(id);
        return ResponseEntity.ok(loanDTO);
    }

    @PutMapping("{id}")
    public ResponseEntity<LoanDTO> updateLoan(@PathVariable Long id, @RequestBody LoanDTO loanDTO) {
        LoanDTO updatedLoanDTO = loanService.updateLoan(id, loanDTO);
        return ResponseEntity.ok(updatedLoanDTO);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteLoanById(@PathVariable Long id) {
        loanService.deleteLoan(id);
        return ResponseEntity.ok("Loan deleted successfully.");
    }
}
