package org.ada.biblioteca.service.user;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.ada.biblioteca.bo.User;
import org.ada.biblioteca.dto.user.UserRequestUpdate;
import org.ada.biblioteca.dto.user.UserResponse;
import org.ada.biblioteca.repository.UserRepository;
import org.ada.biblioteca.util.caster.UserCaster;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserCaster userCaster;

    private static final String USER_NOT_FOUND = "User not found with ID: ";
    private static final String INVALID_USER_ID = "Invalid idUser format for Postgres: ";

    @Value("${spring.profiles.active}")
    private String profile;

    @Override
    public List<UserResponse> getUsers() {
        List<User> users = userRepository.getUsers();
        return users.stream().map(userCaster::userToUserResponse).collect(Collectors.toList());
    }

    @Override
    public UserResponse findUserById(String idUser) {
        if(profile.equals("postgres")) {
            validateIsNumeric(idUser, INVALID_USER_ID + idUser);
        }
        User user = userRepository.findUserById(idUser)
                .orElseThrow(()-> new EntityNotFoundException(USER_NOT_FOUND + idUser));
        return userCaster.userToUserResponse(user);
    }

    @Override
    public UserResponse updateUser(String idUser, UserRequestUpdate userRequestUpdate) {
        if(profile.equals("postgres")) {
            validateIsNumeric(idUser, INVALID_USER_ID + idUser);
        }
        User user = userRepository.findUserById(idUser)
                .orElseThrow(()-> new EntityNotFoundException(USER_NOT_FOUND + idUser));
        user.setName(userRequestUpdate.getName());
        user.setUsername(userRequestUpdate.getUsername());
        user.setEmail(userRequestUpdate.getEmail());
        User update = userRepository.updateUser(user);
        return userCaster.userToUserResponse(update);
    }

    @Override
    public void deleteUser(String idUser) {
        if(profile.equals("postgres")) {
            validateIsNumeric(idUser, INVALID_USER_ID + idUser);
        }
        userRepository.findUserById(idUser)
                .orElseThrow(()-> new EntityNotFoundException(USER_NOT_FOUND + idUser));
        userRepository.deleteUser(idUser);
    }

    private void validateIsNumeric(String value, String errorMessage) {
        if (!isNumeric(value)) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    private boolean isNumeric(String value) {
        return Optional.ofNullable(value)
                .filter(v -> v.matches("\\d+"))
                .isPresent();
    }
}
