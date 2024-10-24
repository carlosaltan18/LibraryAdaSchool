package org.ada.biblioteca.repository.mongo.user;

import lombok.RequiredArgsConstructor;
import org.ada.biblioteca.bo.User;
import org.ada.biblioteca.bo.mongo.UserMongo;
import org.ada.biblioteca.repository.UserRepository;
import org.ada.biblioteca.repository.mongo.book.BookRepositoryNoSql;
import org.ada.biblioteca.util.caster.UserCaster;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Profile("mongo")
@RequiredArgsConstructor
@Repository
public class UserRepositoryMongo implements UserRepository {
    private final UserRepositoryNoSql userRepositoryNoSql;
    private final UserCaster userCaster;

    @Override
    public User createUser(User user) {
        UserMongo userMongo = userCaster.userToUserMongo(user);
        UserMongo newUser = userRepositoryNoSql.save(userMongo);
        return userCaster.userMongoToUser(newUser);
    }

    @Override
    public List<User> getUsers() {
        return userRepositoryNoSql.findAll().stream()
                .map(userCaster::userMongoToUser)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<User> findUserById(String id) {
        Optional<UserMongo> userMongo = userRepositoryNoSql.findById(id);
        return userMongo.map(userCaster::userMongoToUser);
    }

    @Override
    public User updateUser(User user) {
        UserMongo userMongo = userCaster.userToUserMongo(user);
        UserMongo newUser = userRepositoryNoSql.save(userMongo);
        return userCaster.userMongoToUser(newUser);
    }

    @Override
    public void deleteUser(String id) {
        userRepositoryNoSql.deleteById(id);
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        Optional<UserMongo> userMongo = userRepositoryNoSql.findByEmail(email);
        return userMongo.map(userCaster::userMongoToUser);
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        Optional<UserMongo> userMongo = userRepositoryNoSql.findByUsername(username);
        return userMongo.map(userCaster::userMongoToUser);
    }

    @Override
    public Boolean existsById(String id) {
        return userRepositoryNoSql.existsById(id);
    }
}
