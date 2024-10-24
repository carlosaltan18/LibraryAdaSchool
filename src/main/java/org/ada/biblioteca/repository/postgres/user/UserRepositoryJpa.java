package org.ada.biblioteca.repository.postgres.user;

import org.ada.biblioteca.bo.postgres.UserPostgres;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepositoryJpa extends JpaRepository<UserPostgres, Long> {
    Optional<UserPostgres> findByEmail(String email);
    Optional<UserPostgres> findByUsername(String username);
}
