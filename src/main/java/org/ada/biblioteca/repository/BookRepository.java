package org.ada.biblioteca.repository;

import org.ada.biblioteca.bo.Book;
import org.ada.biblioteca.bo.postgres.BookPostgres;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    Book createBook(Book book);
    List<Book> getBooks();
    Optional<Book> findBookById(String idBook);
    Book updateBook(Book book);
    void deleteBook(String idBook);
    Boolean existsById(String idBook);
}
