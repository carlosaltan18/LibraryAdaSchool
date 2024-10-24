package org.ada.biblioteca.service.loan;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.ada.biblioteca.bo.Loan;
import org.ada.biblioteca.bo.User;
import org.ada.biblioteca.dto.loan.LoanResponse;
import org.ada.biblioteca.exception.DataUnrelated;
import org.ada.biblioteca.exception.DateInvalid;
import org.ada.biblioteca.repository.BookRepository;
import org.ada.biblioteca.repository.LoanRepository;
import org.ada.biblioteca.repository.UserRepository;
import org.ada.biblioteca.util.caster.LoanCaster;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class LoanServiceImpl implements LoanService {
    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    private static final String LOAN_NOT_FOUND = "Loan not found of the user with the ID %s and the book with ID %s";
    private static final String INVALID_USER_ID = "Invalid idUser format for Postgres: ";
    private static final String INVALID_BOOK_ID = "Invalid idBook format for Postgres: ";
    private final LoanCaster loanCaster;

    @Value("${spring.profiles.active}")
    private String profile;

    @Override
    public LoanResponse createLoan(String idBook, Loan loan) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String idUser = ((User) userDetails).getId();
        LocalDateTime now = LocalDateTime.now();
        if(profile.equals("postgres")){
            validateIsNumeric(idBook, INVALID_BOOK_ID + idBook);
            validateIsNumeric(idUser, INVALID_USER_ID + idUser);
        }
        if(loan.getLoanDate().isBefore(now)){
            throw new DateInvalid("The loan date cannot be before the current date");
        }
        if(loan.getReturnDate().isBefore(loan.getLoanDate())){
            throw new DateInvalid("The return date cannot be before the loan date");
        }
        if(!bookRepository.existsById(idBook)){
            throw new DataUnrelated("Book not found to establish a data relationship");
        }
        if(!userRepository.existsById(idUser)){
            throw new DataUnrelated("User not found to establish a data relationship");
        }
        loan.setIdUser(idUser);
        loan.setIdBook(idBook);
        Loan newLoan = loanRepository.createLoan(loan);
        return loanCaster.loanToLoanResponse(newLoan);
    }

    @Override
    public List<LoanResponse> getLoans() {
        return loanRepository.getLoans().stream()
                .map(loanCaster::loanToLoanResponse)
                .collect(Collectors.toList());
    }

    @Override
    public LoanResponse findLoanById(String idBook) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String idUser = ((User) userDetails).getId();
        if(profile.equals("postgres")){
            validateIsNumeric(idBook, INVALID_BOOK_ID + idBook);
            validateIsNumeric(idUser, INVALID_USER_ID + idUser);
        }
        Loan loan = loanRepository.findLoanById(idUser, idBook)
                .orElseThrow(() -> new EntityNotFoundException(String.format(LOAN_NOT_FOUND, idUser, idBook)));
        return loanCaster.loanToLoanResponse(loan);
    }

    @Override
    public LoanResponse updateLoan(String idBook, Loan loan) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String idUser = ((User) userDetails).getId();
        if(profile.equals("postgres")){
            validateIsNumeric(idBook, INVALID_BOOK_ID + idBook);
            validateIsNumeric(idUser, INVALID_USER_ID + idUser);
        }
        LocalDateTime now = LocalDateTime.now();
        if(loan.getLoanDate().isBefore(now)){
            throw new DateInvalid("The loan date cannot be before the current date");
        }
        if(loan.getReturnDate().isBefore(loan.getLoanDate())){
            throw new DateInvalid("The return date cannot be before the loan date");
        }
        Loan newLoan = loanRepository.findLoanById(idUser, idBook)
                .orElseThrow(() -> new EntityNotFoundException(String.format(LOAN_NOT_FOUND, idUser, idBook)));
        newLoan.setLoanDate(loan.getLoanDate());
        newLoan.setReturnDate(loan.getReturnDate());
        Loan updateLoan = loanRepository.updateLoan(newLoan);
        return loanCaster.loanToLoanResponse(updateLoan);
    }

    @Override
    public void deleteLoan(String idBook) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String idUser = ((User) userDetails).getId();
        if(profile.equals("postgres")){
            validateIsNumeric(idBook, INVALID_BOOK_ID + idBook);
            validateIsNumeric(idUser, INVALID_USER_ID + idUser);
        }
        loanRepository.findLoanById(idUser, idBook)
                .orElseThrow(() -> new EntityNotFoundException(String.format(LOAN_NOT_FOUND, idUser, idBook)));
        loanRepository.deleteLoan(idUser, idBook);
    }

    private void validateIsNumeric(String value, String errorMessage) {
        if (!isNumeric(value)) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    private boolean isNumeric(String value) {
        return Optional.ofNullable(value)
                .filter(v -> v.matches("\\d+"))
                .isPresent();
    }
}
