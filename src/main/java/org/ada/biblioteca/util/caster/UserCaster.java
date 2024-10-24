package org.ada.biblioteca.util.caster;

import lombok.RequiredArgsConstructor;
import org.ada.biblioteca.bo.Role;
import org.ada.biblioteca.bo.User;
import org.ada.biblioteca.bo.mongo.RoleMongo;
import org.ada.biblioteca.bo.mongo.UserMongo;
import org.ada.biblioteca.bo.postgres.RolePostgres;
import org.ada.biblioteca.bo.postgres.UserPostgres;
import org.ada.biblioteca.dto.user.UserRequest;
import org.ada.biblioteca.dto.user.UserResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class UserCaster {

    private final RoleCaster roleCaster;

    public UserPostgres userToUserPostgres(User user) {
        UserPostgres userPostgres = new UserPostgres();
        userPostgres.setId((user.getId() != null && !user.getId().isEmpty()) ? Long.parseLong(user.getId()) : null);
        userPostgres.setName(user.getName());
        userPostgres.setUsername(user.getUsername());
        userPostgres.setEmail(user.getEmail());
        userPostgres.setPassword(user.getPassword());
        userPostgres.setDateCreation(user.getDateCreation());
        userPostgres.setDateUpdate(user.getDateUpdate());
        Set<RolePostgres> rolesPostgres = user.getRoles().stream()
                .map(roleCaster::roleToRolePostgres)
                .collect(Collectors.toSet());
        userPostgres.setRoles(rolesPostgres);
        return userPostgres;
    }

    public User userPostgresToUser(UserPostgres userPostgres) {
        User user = new User();
        user.setId(String.valueOf(userPostgres.getId()));
        user.setName(userPostgres.getName());
        user.setUsername(userPostgres.getUsername());
        user.setEmail(userPostgres.getEmail());
        user.setPassword(userPostgres.getPassword());
        user.setDateCreation(userPostgres.getDateCreation());
        user.setDateUpdate(userPostgres.getDateUpdate());
        Set<Role> roles = userPostgres.getRoles().stream()
                .map(roleCaster::rolePostgresToRole)
                .collect(Collectors.toSet());
        user.setRoles(roles);
        return user;
    }

    public UserMongo userToUserMongo(User user) {
        UserMongo userMongo = new UserMongo();
        userMongo.setId(user.getId());
        userMongo.setName(user.getName());
        userMongo.setUsername(user.getUsername());
        userMongo.setEmail(user.getEmail());
        userMongo.setPassword(user.getPassword());
        userMongo.setDateCreation(user.getDateCreation());
        userMongo.setDateUpdate(user.getDateUpdate());
        Set<RoleMongo> roles = user.getRoles().stream()
                        .map(roleCaster::roleToRoleMongo)
                                .collect(Collectors.toSet());
        userMongo.setRoles(roles);
        return userMongo;
    }

    public User userMongoToUser(UserMongo userMongo) {
        User user = new User();
        user.setId(userMongo.getId());
        user.setName(userMongo.getName());
        user.setUsername(userMongo.getUsername());
        user.setEmail(userMongo.getEmail());
        user.setPassword(userMongo.getPassword());
        user.setDateCreation(userMongo.getDateCreation());
        user.setDateUpdate(userMongo.getDateUpdate());
        Set<Role> roles = userMongo.getRoles().stream()
                .map(roleCaster::roleMongoToRole)
                .collect(Collectors.toSet());
        user.setRoles(roles);
        return user;
    }

    public UserResponse userToUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setName(user.getName());
        userResponse.setUsername(user.getUsername());
        userResponse.setEmail(user.getEmail());
        userResponse.setDateCreation(user.getDateCreation());
        userResponse.setDateUpdate(user.getDateUpdate());
        return userResponse;
    }
}
