package org.ada.biblioteca.controller;

import lombok.RequiredArgsConstructor;
import org.ada.biblioteca.bo.Loan;
import org.ada.biblioteca.dto.loan.LoanResponse;
import org.ada.biblioteca.service.loan.LoanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/loan")
public class LoanController {
    private final LoanService loanService;

    @PostMapping("/{idBook}")
    public ResponseEntity<LoanResponse> createLoan(@PathVariable String idBook, @RequestBody Loan loan) {
        LoanResponse newLoan = loanService.createLoan(idBook, loan);
        return new ResponseEntity<>(newLoan, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<LoanResponse>> getAllLoans() {
        List<LoanResponse> loanPostgres = loanService.getLoans();
        return new ResponseEntity<>(loanPostgres, HttpStatus.OK);
    }

    @GetMapping("/{idBook}")
    public ResponseEntity<LoanResponse> getLoanById(@PathVariable String idBook) {
        LoanResponse loan = loanService.findLoanById(idBook);
        return new ResponseEntity<>(loan, HttpStatus.OK);
    }

    @PutMapping("/{idBook}")
    public ResponseEntity<LoanResponse> updateLoan(@PathVariable String idBook, @RequestBody Loan loan) {
        LoanResponse newLoan = loanService.updateLoan(idBook, loan);
        return new ResponseEntity<>(newLoan, HttpStatus.OK);
    }

    @DeleteMapping("/{idBook}")
    public ResponseEntity<Map<String, String>> deleteLoan(@PathVariable String idBook) {
        loanService.deleteLoan(idBook);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Book deleted successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
