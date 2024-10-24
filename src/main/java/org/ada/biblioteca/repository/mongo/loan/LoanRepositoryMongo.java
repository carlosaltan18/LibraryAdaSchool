package org.ada.biblioteca.repository.mongo.loan;

import lombok.RequiredArgsConstructor;
import org.ada.biblioteca.bo.Loan;
import org.ada.biblioteca.bo.mongo.LoanMongo;
import org.ada.biblioteca.bo.mongo.LoanMongoId;
import org.ada.biblioteca.repository.LoanRepository;
import org.ada.biblioteca.util.caster.LoanCaster;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Profile("mongo")
@RequiredArgsConstructor
@Service
public class LoanRepositoryMongo implements LoanRepository {
    private final LoanRepositoryNoSql loanRepositoryNoSql;
    private final LoanCaster loanCaster;

    @Override
    public Loan createLoan(Loan loan) {
        LoanMongo loanMongo = loanCaster.loanToLoanMongo(loan);
        LoanMongo newMongo = loanRepositoryNoSql.save(loanMongo);
        return loanCaster.loanMongoToLoan(newMongo);
    }

    @Override
    public List<Loan> getLoans() {
        return loanRepositoryNoSql.findAll().stream()
                .map(loanCaster::loanMongoToLoan)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Loan> findLoanById(String idUser, String idBook) {
        LoanMongoId id = new LoanMongoId();
        id.setIdUser(idUser);
        id.setIdBook(idBook);
        Optional<LoanMongo> loanMongo = loanRepositoryNoSql.findById(id);
        return loanMongo.map(loanCaster::loanMongoToLoan);
    }

    @Override
    public Loan updateLoan(Loan loan) {
        LoanMongo loanMongo = loanCaster.loanToLoanMongo(loan);
        LoanMongo newMongo = loanRepositoryNoSql.save(loanMongo);
        return loanCaster.loanMongoToLoan(newMongo);
    }

    @Override
    public void deleteLoan(String idUser, String idBook) {
        LoanMongoId id = new LoanMongoId();
        id.setIdUser(idUser);
        id.setIdBook(idBook);
        loanRepositoryNoSql.deleteById(id);
    }
}
