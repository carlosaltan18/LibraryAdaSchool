package org.ada.biblioteca.bo.postgres;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "loans")
public class LoanPostgres {
    @EmbeddedId
    private LoanPostgresId idLoan;
    @Column(name = "loan_date")
    private LocalDateTime loanDate;
    @Column(name = "return_date")
    private LocalDateTime returnDate;
}
