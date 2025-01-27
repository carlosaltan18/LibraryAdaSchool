package org.ada.biblioteca.controller;

import lombok.RequiredArgsConstructor;
import org.ada.biblioteca.bo.Book;
import org.ada.biblioteca.service.book.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/book")
public class BookController {
    private final BookService bookService;

    @PostMapping("/save")
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Book bookCreated = bookService.createBook(book);
        return new ResponseEntity<>(bookCreated, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/{idBook}")
    public ResponseEntity<Book> getBookById(@PathVariable String idBook) {
        Book book = bookService.findBookById(idBook);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @PutMapping("/put/{idBook}")
    public ResponseEntity<Book> updateBook(@PathVariable String idBook, @RequestBody Book book) {
        Book bookUpdated = bookService.updateBook(idBook, book);
        return new ResponseEntity<>(bookUpdated, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{idBook}")
    public ResponseEntity<Map<String, String>> deleteBook(@PathVariable String idBook) {
        bookService.deleteBook(idBook);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Book deleted successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
