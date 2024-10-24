package org.ada.biblioteca.controller;

import org.ada.biblioteca.bo.Role;
import org.ada.biblioteca.service.role.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RoleControllerTest {

    private RoleService roleService;
    private RoleController roleController;

    @BeforeEach
    void setUp() {
        roleService = Mockito.mock(RoleService.class);
        roleController = new RoleController(roleService);
    }

    @Test
    void testCreateRole() {
        Role role = new Role();
        role.setIdRole("1");
        role.setRole("ROLE_USER");

        when(roleService.createRole(any(Role.class))).thenReturn(role);

        ResponseEntity<Role> response = roleController.createRole(role);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(role, response.getBody());
    }

    @Test
    void testGetRoles() {
        Role role1 = new Role();
        role1.setIdRole("1");
        role1.setRole("ROLE_USER");

        Role role2 = new Role();
        role2.setIdRole("2");
        role2.setRole("ROLE_ADMIN");

        List<Role> roles = Arrays.asList(role1, role2);
        when(roleService.getRoles()).thenReturn(roles);

        ResponseEntity<List<Role>> response = roleController.getRoles();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(roles, response.getBody());
    }

    @Test
    void testGetRole() {
        Role role = new Role();
        role.setIdRole("1");
        role.setRole("ROLE_USER");

        when(roleService.findRoleById("1")).thenReturn(role);

        ResponseEntity<Role> response = roleController.getRole("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(role, response.getBody());
    }

    @Test
    void testUpdateRole() {
        Role role = new Role();
        role.setIdRole("1");
        role.setRole("ROLE_USER");

        when(roleService.updateRole(eq("1"), any(Role.class))).thenReturn(role);

        ResponseEntity<Role> response = roleController.updateRole("1", role);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(role, response.getBody());
    }

    @Test
    void testDeleteRole() {
        String roleId = "1";
        Map<String, String> expectedResponse = new HashMap<>();
        expectedResponse.put("message", "Role deleted successfully");

        doNothing().when(roleService).deleteRole(roleId);

        ResponseEntity<Map<String, String>> response = roleController.deleteRole(roleId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }
}

