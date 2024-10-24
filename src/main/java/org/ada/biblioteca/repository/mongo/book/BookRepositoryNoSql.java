package org.ada.biblioteca.repository.mongo.book;

import org.ada.biblioteca.bo.mongo.BookMongo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookRepositoryNoSql extends MongoRepository<BookMongo, String> {
}
