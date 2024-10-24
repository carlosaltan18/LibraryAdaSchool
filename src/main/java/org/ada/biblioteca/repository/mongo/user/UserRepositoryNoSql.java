package org.ada.biblioteca.repository.mongo.user;

import org.ada.biblioteca.bo.mongo.UserMongo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepositoryNoSql extends MongoRepository<UserMongo, String> {
    Optional<UserMongo> findByUsername(String username);
    Optional<UserMongo> findByEmail(String email);
}
