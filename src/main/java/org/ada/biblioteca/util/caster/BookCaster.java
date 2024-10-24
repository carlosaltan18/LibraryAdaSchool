package org.ada.biblioteca.util.caster;

import org.ada.biblioteca.bo.Book;
import org.ada.biblioteca.bo.mongo.BookMongo;
import org.ada.biblioteca.bo.postgres.BookPostgres;
import org.springframework.stereotype.Component;

@Component
public class BookCaster {
    public BookPostgres bookToBookPostgres(Book book) {
        BookPostgres bookPostgres = new BookPostgres();
        bookPostgres.setIdBook((book.getIdBook() != null && !book.getIdBook().isEmpty()) ? Long.parseLong(book.getIdBook()) : null);
        bookPostgres.setTitle(book.getTitle());
        bookPostgres.setAuthor(book.getAuthor());
        bookPostgres.setIsbn(book.getIsbn());
        return bookPostgres;
    }

    public Book bookPostgresToBook(BookPostgres bookPostgres) {
        Book book = new Book();
        book.setIdBook(String.valueOf(bookPostgres.getIdBook()));
        book.setTitle(bookPostgres.getTitle());
        book.setAuthor(bookPostgres.getAuthor());
        book.setIsbn(bookPostgres.getIsbn());
        return book;
    }

    public BookMongo bookToBookMongo(Book book){
        BookMongo bookMongo = new BookMongo();
        bookMongo.setIdBook(book.getIdBook());
        bookMongo.setTitle(book.getTitle());
        bookMongo.setAuthor(book.getAuthor());
        bookMongo.setIsbn(book.getIsbn());
        return bookMongo;
    }

    public Book bookMongoToBook(BookMongo bookMongo){
        Book book = new Book();
        book.setIdBook(bookMongo.getIdBook());
        book.setTitle(bookMongo.getTitle());
        book.setAuthor(bookMongo.getAuthor());
        book.setIsbn(bookMongo.getIsbn());
        return book;
    }
}
