package org.ada.biblioteca.repository.postgres.loan;

import lombok.RequiredArgsConstructor;
import org.ada.biblioteca.bo.Loan;
import org.ada.biblioteca.bo.postgres.LoanPostgres;
import org.ada.biblioteca.bo.postgres.LoanPostgresId;
import org.ada.biblioteca.repository.LoanRepository;
import org.ada.biblioteca.util.caster.LoanCaster;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Profile("postgres")
@RequiredArgsConstructor
@Repository
public class LoanRepositoryPostgres implements LoanRepository {
    private final LoanRepositoryJpa loanRepositoryJpa;
    private final LoanCaster loanCaster;

    @Override
    public Loan createLoan(Loan loan) {
        LoanPostgres loanPostgres = loanCaster.loanToLoanPostgres(loan);
        LoanPostgres newLoan = loanRepositoryJpa.save(loanPostgres);
        return loanCaster.loanPostgresToLoan(newLoan);
    }

    @Override
    public List<Loan> getLoans() {
        return loanRepositoryJpa.findAll().stream()
                .map(loanCaster::loanPostgresToLoan)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Loan> findLoanById(String idUser, String idBook) {
        LoanPostgresId id = new LoanPostgresId();
        id.setIdUser(Long.parseLong(idUser));
        id.setIdBook(Long.parseLong(idBook));
        Optional<LoanPostgres> loanPostgres = loanRepositoryJpa.findById(id);
        return loanPostgres.map(loanCaster::loanPostgresToLoan);
    }

    @Override
    public Loan updateLoan(Loan loan) {
        LoanPostgres loanPostgres = loanCaster.loanToLoanPostgres(loan);
        LoanPostgres newLoan = loanRepositoryJpa.save(loanPostgres);
        return loanCaster.loanPostgresToLoan(newLoan);
    }

    @Override
    public void deleteLoan(String idUser, String idBook) {
        LoanPostgresId id = new LoanPostgresId();
        id.setIdUser(Long.parseLong(idUser));
        id.setIdBook(Long.parseLong(idBook));
        loanRepositoryJpa.deleteById(id);
    }
}
