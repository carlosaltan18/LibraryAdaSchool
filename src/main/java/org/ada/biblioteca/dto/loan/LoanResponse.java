package org.ada.biblioteca.dto.loan;

import lombok.Data;
import org.ada.biblioteca.bo.Book;

import java.time.LocalDateTime;

@Data
public class LoanResponse {
    private UserLoanResponse user;
    private Book book;
    private LocalDateTime loanDate;
    private LocalDateTime returnDate;
}
