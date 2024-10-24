package org.ada.biblioteca.repository.postgres.book;

import lombok.RequiredArgsConstructor;
import org.ada.biblioteca.bo.Book;
import org.ada.biblioteca.bo.postgres.BookPostgres;
import org.ada.biblioteca.repository.BookRepository;
import org.ada.biblioteca.util.caster.BookCaster;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Profile("postgres")
@RequiredArgsConstructor
@Repository
public class BookRepositoryPostgres implements BookRepository {

    private final BookRepositoryJpa bookRepositoryJpa;
    private final BookCaster bookCaster;

    @Override
    public Book createBook(Book book) {
        BookPostgres bookPostgres = bookCaster.bookToBookPostgres(book);
        BookPostgres newBook = bookRepositoryJpa.save(bookPostgres);
        return bookCaster.bookPostgresToBook(newBook);
    }

    @Override
    public List<Book> getBooks() {
        return bookRepositoryJpa.findAll().stream()
                .map(bookCaster::bookPostgresToBook)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Book> findBookById(String idBook) {
        Optional<BookPostgres> bookPostgres = bookRepositoryJpa.findById(Long.parseLong(idBook));
        return bookPostgres.map(bookCaster::bookPostgresToBook);
    }

    @Override
    public Book updateBook(Book book) {
        BookPostgres bookPostgres = bookCaster.bookToBookPostgres(book);
        BookPostgres newBook = bookRepositoryJpa.save(bookPostgres);
        return bookCaster.bookPostgresToBook(newBook);
    }

    @Override
    public void deleteBook(String idBook) {
        bookRepositoryJpa.deleteById(Long.parseLong(idBook));
    }

    @Override
    public Boolean existsById(String idBook) {
        return bookRepositoryJpa.existsById(Long.parseLong(idBook));
    }
}
