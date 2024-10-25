package org.ada.biblioteca.controller;


import org.ada.biblioteca.dto.user.UserRequestUpdate;
import org.ada.biblioteca.dto.user.UserResponse;
import org.ada.biblioteca.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUsers() {
        UserResponse userResponse = new UserResponse();
        when(userService.getUsers()).thenReturn(Collections.singletonList(userResponse));

        ResponseEntity<List<UserResponse>> response = userController.getUsers();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(userResponse, response.getBody().get(0));
    }

    @Test
    void testGetUser() {
        String userId = "123456";
        UserResponse userResponse = new UserResponse();
        when(userService.findUserById(userId)).thenReturn(userResponse);
        ResponseEntity<UserResponse> response = userController.getUser(userId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userResponse, response.getBody());
    }

    @Test
    void testUpdateUser() {
        String userId = "123456";
        UserRequestUpdate userRequestUpdate = new UserRequestUpdate();
        UserResponse updatedUserResponse = new UserResponse();
        when(userService.updateUser(eq(userId), any(UserRequestUpdate.class))).thenReturn(updatedUserResponse);
        ResponseEntity<UserResponse> response = userController.updateUser(userId, userRequestUpdate);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedUserResponse, response.getBody());
    }

    @Test
    void testDeleteUser() {
        String userId = "123456";
        ResponseEntity<Map<String, String>> response = userController.deleteUser(userId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().containsKey("message"));
        assertEquals("User deleted successfully", response.getBody().get("message"));
        verify(userService, times(1)).deleteUser(userId);
    }
}
