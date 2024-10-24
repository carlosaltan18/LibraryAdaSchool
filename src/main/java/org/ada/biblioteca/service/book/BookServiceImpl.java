package org.ada.biblioteca.service.book;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.ada.biblioteca.bo.Book;
import org.ada.biblioteca.repository.BookRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private static final String BOOK_NOT_FOUND = "Book not found with ID: ";
    private static final String INVALID_BOOK_ID = "Invalid idBook format for Postgres: ";

    @Value("${spring.profiles.active}")
    private String profile;

    @Override
    public Book createBook(Book book) {
        return bookRepository.createBook(book);
    }

    @Override
    public List<Book> getBooks() {
        return bookRepository.getBooks();
    }

    @Override
    public Book findBookById(String idBook) {
        if(profile.equals("postgres")) {
            validateIsNumeric(idBook, INVALID_BOOK_ID + idBook);
        }
        return bookRepository.findBookById(idBook)
                .orElseThrow(() -> new EntityNotFoundException(BOOK_NOT_FOUND + idBook));
    }

    @Override
    public Book updateBook(String idBook, Book book) {
        if(profile.equals("postgres")) {
            validateIsNumeric(idBook, INVALID_BOOK_ID + idBook);
        }
        Book bookFound = bookRepository.findBookById(idBook)
                .orElseThrow(() -> new EntityNotFoundException(BOOK_NOT_FOUND + idBook));
        bookFound.setTitle(book.getTitle());
        bookFound.setAuthor(book.getAuthor());
        bookFound.setIsbn(book.getIsbn());
        return bookRepository.updateBook(bookFound);
    }

    @Override
    public void deleteBook(String idBook) {
        if(profile.equals("postgres")) {
            validateIsNumeric(idBook, INVALID_BOOK_ID + idBook);
        }
        if(bookRepository.findBookById(idBook).isEmpty()) {
            throw new EntityNotFoundException(BOOK_NOT_FOUND + idBook);
        }
        bookRepository.deleteBook(idBook);
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
