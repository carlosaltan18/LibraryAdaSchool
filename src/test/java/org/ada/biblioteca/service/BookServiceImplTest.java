package org.ada.biblioteca.service;

import jakarta.persistence.EntityNotFoundException;
import org.ada.biblioteca.bo.Book;
import org.ada.biblioteca.repository.BookRepository;
import org.ada.biblioteca.service.book.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(bookService, "profile", "postgres");
    }

    @Test
    void testCreateBook_Success() {
        Book book = new Book();
        book.setTitle("Harry potter");
        book.setAuthor("JK ROWLING");
        book.setIsbn("123456562");

        when(bookRepository.createBook(book)).thenReturn(book);

        Book result = bookService.createBook(book);

        assertNotNull(result);
        assertEquals("Harry potter", result.getTitle());
        verify(bookRepository, times(1)).createBook(book);
    }

    @Test
    void testGetBooks_Success() {
        Book book = new Book();
        book.setTitle("Harry potter");
        book.setAuthor("JK ROWLING");
        book.setIsbn("123456562");

        when(bookRepository.getBooks()).thenReturn(Collections.singletonList(book));

        List<Book> result = bookService.getBooks();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Harry potter", result.get(0).getTitle());
        verify(bookRepository, times(1)).getBooks();
    }

    @Test
    void testFindBookById_Success() {
        String idBook = "1";
        Book book = new Book();
        book.setTitle("Harry potter");
        when(bookRepository.findBookById(idBook)).thenReturn(Optional.of(book));
        Book result = bookService.findBookById(idBook);
        assertNotNull(result);
        assertEquals("Harry potter", result.getTitle());
        verify(bookRepository, times(1)).findBookById(idBook);
    }

    @Test
    void testFindBookById_NotFound() {
        String idBook = "1";
        when(bookRepository.findBookById(idBook)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            bookService.findBookById(idBook);
        });
        assertEquals("Book not found with ID: 1", exception.getMessage());
        verify(bookRepository, times(1)).findBookById(idBook);
    }

    @Test
    void testUpdateBook_Success() {
        String idBook = "1";
        Book bookToUpdate = new Book();
        bookToUpdate.setTitle("El conjuro");
        bookToUpdate.setAuthor("alguien");
        bookToUpdate.setIsbn("562324465");

        Book existingBook = new Book();
        existingBook.setTitle("New");
        existingBook.setAuthor("New");
        existingBook.setIsbn("455684");

        when(bookRepository.findBookById(idBook)).thenReturn(Optional.of(existingBook));
        when(bookRepository.updateBook(existingBook)).thenReturn(existingBook);

        Book result = bookService.updateBook(idBook, bookToUpdate);

        assertNotNull(result);
        assertEquals("El conjuro", result.getTitle());
        verify(bookRepository, times(1)).findBookById(idBook);
        verify(bookRepository, times(1)).updateBook(existingBook);
    }

    @Test
    void testUpdateBook_NotFound() {
        String idBook = "8";
        Book bookToUpdate = new Book();
        when(bookRepository.findBookById(idBook)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            bookService.updateBook(idBook, bookToUpdate);
        });

        assertEquals("Book not found with ID: 8", exception.getMessage());
        verify(bookRepository, times(1)).findBookById(idBook);
    }

    @Test
    void testDeleteBook_Success() {
        String idBook = "1";
        Book book = new Book();
        when(bookRepository.findBookById(idBook)).thenReturn(Optional.of(book));

        bookService.deleteBook(idBook);

        verify(bookRepository, times(1)).findBookById(idBook);
        verify(bookRepository, times(1)).deleteBook(idBook);
    }

    @Test
    void testDeleteBook_NotFound() {
        String idBook = "7";
        when(bookRepository.findBookById(idBook)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            bookService.deleteBook(idBook);
        });
        assertEquals("Book not found with ID: 7", exception.getMessage());
        verify(bookRepository, times(1)).findBookById(idBook);
    }
}
