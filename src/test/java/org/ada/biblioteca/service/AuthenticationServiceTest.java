package org.ada.biblioteca.service;

import jakarta.persistence.EntityNotFoundException;
import org.ada.biblioteca.bo.Role;
import org.ada.biblioteca.bo.User;
import org.ada.biblioteca.dto.user.UserRequest;
import org.ada.biblioteca.dto.user.UserRequestLogin;
import org.ada.biblioteca.repository.RoleRepository;
import org.ada.biblioteca.repository.UserRepository;
import org.ada.biblioteca.service.auth.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSignup_Success() {
        // Arrange
        UserRequest userRequest = new UserRequest();
        userRequest.setName("Test User");
        userRequest.setUsername("testuser");
        userRequest.setEmail("test@example.com");
        userRequest.setPassword("password");

        Role role = new Role();
        role.setRole("ROLE_USER");

        when(passwordEncoder.encode(userRequest.getPassword())).thenReturn("encodedPassword");
        when(roleRepository.findRoleByName("ROLE_USER")).thenReturn(Optional.of(role));

        User user = new User();
        user.setRoles(new HashSet<>()); // Inicializar el Set de roles
        when(userRepository.createUser(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        User result = authenticationService.signup(userRequest);

        // Assert
        assertNotNull(result);
        assertEquals("Test User", result.getName());
        assertEquals("testuser", result.getUsername());
        assertEquals("test@example.com", result.getEmail());
        assertEquals("encodedPassword", result.getPassword());
        assertEquals(1, result.getRoles().size());
        assertTrue(result.getRoles().contains(role)); // Verificar que el rol está presente
        verify(userRepository, times(1)).createUser(any(User.class));
    }

    @Test
    void testSignup_RoleNotFound() {
        // Arrange
        UserRequest userRequest = new UserRequest();
        userRequest.setName("Test User");
        userRequest.setUsername("testuser");
        userRequest.setEmail("test@example.com");
        userRequest.setPassword("password");

        when(roleRepository.findRoleByName("ROLE_USER")).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            authenticationService.signup(userRequest);
        });

        assertEquals("error creating user, could not assign a role", exception.getMessage());
        verify(userRepository, never()).createUser(any(User.class));
    }

    @Test
    void testLogin_Success() {
        // Arrange
        UserRequestLogin userRequestLogin = new UserRequestLogin();
        userRequestLogin.setUsername("testuser");
        userRequestLogin.setPassword("password");

        User user = new User();
        user.setUsername("testuser");
        user.setPassword("encodedPassword");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);
        when(userRepository.findUserByUsername(userRequestLogin.getUsername())).thenReturn(Optional.of(user));

        // Act
        User result = authenticationService.login(userRequestLogin);

        // Assert
        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        verify(userRepository, times(1)).findUserByUsername(userRequestLogin.getUsername());
    }

    @Test
    void testLogin_UserNotFound() {
        // Arrange
        UserRequestLogin userRequestLogin = new UserRequestLogin();
        userRequestLogin.setUsername("testuser");
        userRequestLogin.setPassword("password");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);
        when(userRepository.findUserByUsername(userRequestLogin.getUsername())).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            authenticationService.login(userRequestLogin);
        });

        assertEquals("error authenticating user, could not find user", exception.getMessage());
        verify(userRepository, times(1)).findUserByUsername(userRequestLogin.getUsername());
    }

    @Test
    void testGetRolesName_Success() {
        // Arrange
        User user = new User();
        Role role1 = new Role();
        role1.setRole("ROLE_USER");
        Role role2 = new Role();
        role2.setRole("ROLE_ADMIN");

        Set<Role> roles = new HashSet<>();
        roles.add(role1);
        roles.add(role2);
        user.setRoles(roles);

        // Act
        List<String> rolesNames = authenticationService.getRolesName(user);

        // Assert
        assertNotNull(rolesNames);
        assertEquals(2, rolesNames.size());
        assertTrue(rolesNames.contains("ROLE_USER"));
        assertTrue(rolesNames.contains("ROLE_ADMIN"));
    }
}

