package org.ada.biblioteca.bo.mongo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "books")
public class BookMongo {
    @Id
    private String idBook;
    private String title;
    private String author;
    private String isbn;
}
