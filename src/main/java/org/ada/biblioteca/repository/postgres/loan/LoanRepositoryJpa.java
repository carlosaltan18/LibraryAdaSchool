package org.ada.biblioteca.repository.postgres.loan;

import org.ada.biblioteca.bo.postgres.LoanPostgres;
import org.ada.biblioteca.bo.postgres.LoanPostgresId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepositoryJpa extends JpaRepository<LoanPostgres, LoanPostgresId> {
}
