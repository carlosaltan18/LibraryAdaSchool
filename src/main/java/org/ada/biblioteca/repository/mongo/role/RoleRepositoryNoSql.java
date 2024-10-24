package org.ada.biblioteca.repository.mongo.role;

import org.ada.biblioteca.bo.mongo.RoleMongo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepositoryNoSql extends MongoRepository<RoleMongo, String> {
    Optional<RoleMongo> findByRole(String role);
}
