package org.ada.biblioteca.repository.postgres.user;

import lombok.RequiredArgsConstructor;
import org.ada.biblioteca.bo.User;
import org.ada.biblioteca.bo.postgres.UserPostgres;
import org.ada.biblioteca.repository.UserRepository;
import org.ada.biblioteca.util.caster.UserCaster;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Profile("postgres")
@Repository
@RequiredArgsConstructor
public class UserRepositoryPostgres implements UserRepository {

    private final UserRepositoryJpa userRepositoryJpa;
    private final UserCaster userCaster;

    @Override
    public User createUser(User user) {
        UserPostgres userPostgres = userCaster.userToUserPostgres(user);
        UserPostgres newUser = userRepositoryJpa.save(userPostgres);
        return userCaster.userPostgresToUser(newUser);
    }

    @Override
    public List<User> getUsers() {
        return userRepositoryJpa.findAll().stream()
                .map(userCaster::userPostgresToUser)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<User> findUserById(String id) {
        Optional<UserPostgres> userPostgres = userRepositoryJpa.findById(Long.parseLong(id));
        return userPostgres.map(userCaster::userPostgresToUser);
    }

    @Override
    public User updateUser(User user) {
        UserPostgres userPostgres = userCaster.userToUserPostgres(user);
        UserPostgres newUser = userRepositoryJpa.save(userPostgres);
        return userCaster.userPostgresToUser(newUser);
    }

    @Override
    public void deleteUser(String idUser) {
        userRepositoryJpa.deleteById(Long.parseLong(idUser));
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        Optional<UserPostgres> userPostgres = userRepositoryJpa.findByEmail(email);
        return userPostgres.map(userCaster::userPostgresToUser);
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        Optional<UserPostgres> userPostgres = userRepositoryJpa.findByUsername(username);
        return userPostgres.map(userCaster::userPostgresToUser);
    }

    @Override
    public Boolean existsById(String id) {
        return userRepositoryJpa.existsById(Long.parseLong(id));
    }
}
