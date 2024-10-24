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
        // Arrange
        User user1 = new User();
        User user2 = new User();
        UserResponse response1 = new UserResponse();
        UserResponse response2 = new UserResponse();

        when(userRepository.getUsers()).thenReturn(Arrays.asList(user1, user2));
        when(userCaster.userToUserResponse(user1)).thenReturn(response1);
        when(userCaster.userToUserResponse(user2)).thenReturn(response2);

        // Act
        var result = userService.getUsers();

        // Assert
        assertEquals(2, result.size());

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userCaster, times(2)).userToUserResponse(captor.capture());


        List<User> capturedUsers = captor.getAllValues();
        assertEquals(user1, capturedUsers.get(0));
        assertEquals(user2, capturedUsers.get(1));
    }


    @Test
    void testFindUserById_UserFound() {
        // Arrange
        String idUser = "1";
        User user = new User();
        UserResponse userResponse = new UserResponse();
        when(userRepository.findUserById(idUser)).thenReturn(Optional.of(user));
        when(userCaster.userToUserResponse(user)).thenReturn(userResponse);

        // Act
        var result = userService.findUserById(idUser);

        // Assert
        assertNotNull(result);
        assertEquals(userResponse, result);
        verify(userRepository, times(1)).findUserById(idUser);
    }

    @Test
    void testFindUserById_UserNotFound() {
        // Arrange
        String idUser = "1";
        when(userRepository.findUserById(idUser)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            userService.findUserById(idUser);
        });
        assertTrue(exception.getMessage().contains("User not found with ID: " + idUser));
        verify(userRepository, times(1)).findUserById(idUser);
    }

    @Test
    void testUpdateUser() {
        // Arrange
        String idUser = "1";
        UserRequestUpdate requestUpdate = new UserRequestUpdate();
        requestUpdate.setName("Lionel Messi");
        requestUpdate.setUsername("messi");
        requestUpdate.setEmail("messi@gmail.com");

        User user = new User();
        User updatedUser = new User();
        UserResponse userResponse = new UserResponse();

        when(userRepository.findUserById(idUser)).thenReturn(Optional.of(user));
        when(userRepository.updateUser(user)).thenReturn(updatedUser);
        when(userCaster.userToUserResponse(updatedUser)).thenReturn(userResponse);

        // Act
        var result = userService.updateUser(idUser, requestUpdate);

        // Assert
        assertNotNull(result);
        assertEquals(userResponse, result);
        verify(userRepository, times(1)).findUserById(idUser);
        verify(userRepository, times(1)).updateUser(user);
    }

    @Test
    void testDeleteUser_UserFound() {
        // Arrange
        String idUser = "1";
        User user = new User();
        when(userRepository.findUserById(idUser)).thenReturn(Optional.of(user));

        // Act
        userService.deleteUser(idUser);

        // Assert
        verify(userRepository, times(1)).findUserById(idUser);
        verify(userRepository, times(1)).deleteUser(idUser);
    }

    @Test
    void testDeleteUser_UserNotFound() {
        // Arrange
        String idUser = "1";
        when(userRepository.findUserById(idUser)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            userService.deleteUser(idUser);
        });
        assertTrue(exception.getMessage().contains("User not found with ID: " + idUser));
        verify(userRepository, times(1)).findUserById(idUser);
    }
}
