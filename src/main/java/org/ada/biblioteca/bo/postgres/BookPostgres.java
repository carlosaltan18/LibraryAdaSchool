package org.ada.biblioteca.bo.postgres;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Table(name = "books")
@Entity
public class BookPostgres {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_book")
    private Long idBook;
    private String title;
    private String author;
    private String isbn;
}
