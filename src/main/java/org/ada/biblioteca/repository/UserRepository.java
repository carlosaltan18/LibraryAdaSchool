package org.ada.biblioteca.repository;

import org.ada.biblioteca.bo.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User createUser(User user);
    List<User> getUsers();
    Optional<User> findUserById(String id);
    User updateUser(User user);
    void deleteUser(String id);
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByUsername(String username);
    Boolean existsById(String id);
}
