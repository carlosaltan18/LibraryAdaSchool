package org.ada.biblioteca.util.caster;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.ada.biblioteca.bo.Book;
import org.ada.biblioteca.bo.Loan;
import org.ada.biblioteca.bo.User;
import org.ada.biblioteca.bo.mongo.LoanMongo;
import org.ada.biblioteca.bo.mongo.LoanMongoId;
import org.ada.biblioteca.bo.postgres.LoanPostgres;
import org.ada.biblioteca.bo.postgres.LoanPostgresId;
import org.ada.biblioteca.dto.loan.LoanResponse;
import org.ada.biblioteca.dto.loan.UserLoanResponse;
import org.ada.biblioteca.repository.BookRepository;
import org.ada.biblioteca.repository.UserRepository;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class LoanCaster {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public LoanPostgres loanToLoanPostgres(Loan loan) {
        LoanPostgres loanPostgres = new LoanPostgres();
        LoanPostgresId loanPostgresId = new LoanPostgresId();
        loanPostgresId.setIdUser((loan.getIdUser() != null && !loan.getIdUser().isEmpty()) ? Long.parseLong(loan.getIdUser()) : null);
        loanPostgresId.setIdBook((loan.getIdBook() != null && !loan.getIdBook().isEmpty()) ? Long.parseLong(loan.getIdBook()) : null);
        loanPostgres.setIdLoan(loanPostgresId);
        loanPostgres.setLoanDate(loan.getLoanDate());
        loanPostgres.setReturnDate(loan.getReturnDate());
        return loanPostgres;
    }

    public Loan loanPostgresToLoan(LoanPostgres loanPostgres) {
        Loan loan = new Loan();
        loan.setIdUser(String.valueOf(loanPostgres.getIdLoan().getIdUser()));
        loan.setIdBook(String.valueOf(loanPostgres.getIdLoan().getIdBook()));
        loan.setLoanDate(loanPostgres.getLoanDate());
        loan.setReturnDate(loanPostgres.getReturnDate());
        return loan;
    }

    public LoanMongo loanToLoanMongo(Loan loan){
        LoanMongo loanMongo = new LoanMongo();
        LoanMongoId loanMongoId = new LoanMongoId();
        loanMongoId.setIdBook(loan.getIdBook());
        loanMongoId.setIdUser(loan.getIdUser());
        loanMongo.setIdLoan(loanMongoId);
        loanMongo.setLoanDate(loan.getLoanDate());
        loanMongo.setReturnDate(loan.getReturnDate());
        return loanMongo;
    }

    public Loan loanMongoToLoan(LoanMongo loanMongo){
        Loan loan = new Loan();
        loan.setIdUser(loanMongo.getIdLoan().getIdUser());
        loan.setIdBook(loanMongo.getIdLoan().getIdBook());
        loan.setLoanDate(loanMongo.getLoanDate());
        loan.setReturnDate(loanMongo.getReturnDate());
        return loan;
    }

    public LoanResponse loanToLoanResponse(Loan loan){
        LoanResponse loanResponse = new LoanResponse();
        UserLoanResponse userResponse = new UserLoanResponse();
        Book book = bookRepository.findBookById(loan.getIdBook())
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));
        User user = userRepository.findUserById(loan.getIdUser())
                        .orElseThrow(() -> new EntityNotFoundException("User not found"));
        userResponse.setId(user.getId());
        userResponse.setName(user.getName());
        userResponse.setEmail(user.getEmail());
        userResponse.setUsername(user.getUsername());
        loanResponse.setUser(userResponse);
        loanResponse.setBook(book);
        loanResponse.setLoanDate(loan.getLoanDate());
        loanResponse.setReturnDate(loan.getReturnDate());
        return  loanResponse;
    }
}
