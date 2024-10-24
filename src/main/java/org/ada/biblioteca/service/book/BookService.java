package org.ada.biblioteca.service.book;

import org.ada.biblioteca.bo.Book;
import org.ada.biblioteca.bo.postgres.BookPostgres;

import java.util.List;

public interface BookService {
    Book createBook(Book book);
    List<Book> getBooks();
    Book findBookById(String idBook);
    Book updateBook(String idBook, Book book);
    void deleteBook(String idBook);
}
