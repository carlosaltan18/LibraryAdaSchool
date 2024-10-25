package org.ada.biblioteca.service;

import jakarta.persistence.EntityNotFoundException;
import org.ada.biblioteca.bo.User;
import org.ada.biblioteca.dto.user.UserRequestUpdate;
import org.ada.biblioteca.dto.user.UserResponse;
import org.ada.biblioteca.repository.UserRepository;
import org.ada.biblioteca.service.user.UserServiceImpl;
import org.ada.biblioteca.util.caster.UserCaster;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserCaster userCaster;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(userService, "profile", "postgres");
    }

    @Test
    void testGetUsers() {
        User user1 = new User();
        User user2 = new User();
        UserResponse response1 = new UserResponse();
        UserResponse response2 = new UserResponse();

        when(userRepository.getUsers()).thenReturn(Arrays.asList(user1, user2));
        when(userCaster.userToUserResponse(user1)).thenReturn(response1);
        when(userCaster.userToUserResponse(user2)).thenReturn(response2);
        var result = userService.getUsers();

        assertEquals(2, result.size());
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userCaster, times(2)).userToUserResponse(captor.capture());
        List<User> capturedUsers = captor.getAllValues();
        assertEquals(user1, capturedUsers.get(0));
        assertEquals(user2, capturedUsers.get(1));
    }


    @Test
    void testFindUserById_UserFound() {
        String idUser = "4";
        User user = new User();
        UserResponse userResponse = new UserResponse();
        when(userRepository.findUserById(idUser)).thenReturn(Optional.of(user));
        when(userCaster.userToUserResponse(user)).thenReturn(userResponse);

        var result = userService.findUserById(idUser);

        assertNotNull(result);
        assertEquals(userResponse, result);
        verify(userRepository, times(1)).findUserById(idUser);
    }

    @Test
    void testFindUserById_UserNotFound() {
        String idUser = "8";
        when(userRepository.findUserById(idUser)).thenReturn(Optional.empty());
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            userService.findUserById(idUser);
        });
        assertTrue(exception.getMessage().contains("User not found with ID: " + idUser));
        verify(userRepository, times(1)).findUserById(idUser);
    }

    @Test
    void testUpdateUser() {
        // Arrange
        String idUser = "4";
        UserRequestUpdate requestUpdate = new UserRequestUpdate();
        requestUpdate.setName("Crlos");
        requestUpdate.setUsername("Cortez");
        requestUpdate.setEmail("ccort@gmail.com");
        User user = new User();
        User updatedUser = new User();
        UserResponse userResponse = new UserResponse();
        when(userRepository.findUserById(idUser)).thenReturn(Optional.of(user));
        when(userRepository.updateUser(user)).thenReturn(updatedUser);
        when(userCaster.userToUserResponse(updatedUser)).thenReturn(userResponse);

        var result = userService.updateUser(idUser, requestUpdate);

        assertNotNull(result);
        assertEquals(userResponse, result);
        verify(userRepository, times(1)).findUserById(idUser);
        verify(userRepository, times(1)).updateUser(user);
    }

    @Test
    void testDeleteUser_UserFound() {
        String idUser = "4";
        User user = new User();
        when(userRepository.findUserById(idUser)).thenReturn(Optional.of(user));
        userService.deleteUser(idUser);
        verify(userRepository, times(1)).findUserById(idUser);
        verify(userRepository, times(1)).deleteUser(idUser);
    }

    @Test
    void testDeleteUser_UserNotFound() {
        String idUser = "2";
        when(userRepository.findUserById(idUser)).thenReturn(Optional.empty());
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            userService.deleteUser(idUser);
        });
        assertTrue(exception.getMessage().contains("User not found with ID: " + idUser));
        verify(userRepository, times(1)).findUserById(idUser);
    }
}
