package org.ada.biblioteca.repository.postgres.book;

import org.ada.biblioteca.bo.postgres.BookPostgres;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepositoryJpa extends JpaRepository<BookPostgres, Long> {
}
