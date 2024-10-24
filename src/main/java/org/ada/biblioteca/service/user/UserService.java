package org.ada.biblioteca.service.user;

import org.ada.biblioteca.dto.user.UserRequestUpdate;
import org.ada.biblioteca.dto.user.UserResponse;

import java.util.List;

public interface UserService {
    List<UserResponse> getUsers();
    UserResponse findUserById(String idUser);
    UserResponse updateUser(String idUser, UserRequestUpdate userRequestUpdate);
    void deleteUser(String idUser);
}
