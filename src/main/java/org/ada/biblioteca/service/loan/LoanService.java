package org.ada.biblioteca.service.loan;

import org.ada.biblioteca.bo.Loan;
import org.ada.biblioteca.dto.loan.LoanResponse;

import java.util.List;

public interface LoanService {
    LoanResponse createLoan(String idBook, Loan loan);
    List<LoanResponse> getLoans();
    LoanResponse findLoanById(String idBook);
    LoanResponse updateLoan(String idBook, Loan loan);
    void deleteLoan(String idBook);
}
