package org.ada.biblioteca.service;

import jakarta.persistence.EntityNotFoundException;
import org.ada.biblioteca.bo.Role;
import org.ada.biblioteca.repository.RoleRepository;
import org.ada.biblioteca.service.role.RoleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoleServiceImplTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(roleService, "profile", "postgres");
    }

    @Test
    void testFindRoleById_Success() {
        // Arrange
        String idRole = "1";
        Role role = new Role();
        role.setRole("USER");
        when(roleRepository.findRoleById(idRole)).thenReturn(Optional.of(role));

        // Act
        Role result = roleService.findRoleById(idRole);

        // Assert
        assertNotNull(result);
        assertEquals("USER", result.getRole());
        verify(roleRepository, times(1)).findRoleById(idRole);
    }

    @Test
    void testFindRoleById_NotFound() {
        // Arrange
        String idRole = "1";
        when(roleRepository.findRoleById(idRole)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            roleService.findRoleById(idRole);
        });

        assertEquals("Role not found with id 1", exception.getMessage());
        verify(roleRepository, times(1)).findRoleById(idRole);
    }

    @Test
    void testUpdateRole_Success() {
        // Arrange
        String idRole = "1";
        Role role = new Role();
        role.setRole("ADMIN");
        Role existingRole = new Role();
        existingRole.setRole("USER");
        when(roleRepository.findRoleById(idRole)).thenReturn(Optional.of(existingRole));
        when(roleRepository.updateRole(existingRole)).thenReturn(existingRole);

        // Act
        Role result = roleService.updateRole(idRole, role);

        // Assert
        assertNotNull(result);
        assertEquals("ADMIN", result.getRole());
        verify(roleRepository, times(1)).findRoleById(idRole);
        verify(roleRepository, times(1)).updateRole(existingRole);
    }

    @Test
    void testUpdateRole_NotFound() {
        // Arrange
        String idRole = "1";
        Role role = new Role();
        when(roleRepository.findRoleById(idRole)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            roleService.updateRole(idRole, role);
        });

        assertEquals("Role not found with id 1", exception.getMessage());
        verify(roleRepository, times(1)).findRoleById(idRole);
    }

    @Test
    void testDeleteRole_Success() {
        // Arrange
        String idRole = "1";
        Role role = new Role();
        when(roleRepository.findRoleById(idRole)).thenReturn(Optional.of(role));

        // Act
        roleService.deleteRole(idRole);

        // Assert
        verify(roleRepository, times(1)).findRoleById(idRole);
        verify(roleRepository, times(1)).deleteRole(idRole);
    }

    @Test
    void testDeleteRole_NotFound() {
        // Arrange
        String idRole = "1";
        when(roleRepository.findRoleById(idRole)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            roleService.deleteRole(idRole);
        });

        assertEquals("Role not found with id 1", exception.getMessage());
        verify(roleRepository, times(1)).findRoleById(idRole);
    }
}
