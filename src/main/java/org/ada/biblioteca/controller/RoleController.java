package org.ada.biblioteca.controller;

import lombok.RequiredArgsConstructor;
import org.ada.biblioteca.bo.Role;
import org.ada.biblioteca.service.role.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/role")
public class RoleController {
    private final RoleService roleService;

    @PostMapping
    public ResponseEntity<Role> createRole(@RequestBody Role role) {
        Role createdRole = roleService.createRole(role);
        return new ResponseEntity<>(createdRole, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Role>> getRoles() {
        List<Role> roles = roleService.getRoles();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @GetMapping("/{idRole}")
    public ResponseEntity<Role> getRole(@PathVariable("idRole") String idRole) {
        Role role = roleService.findRoleById(idRole);
        return new ResponseEntity<>(role, HttpStatus.OK);
    }
    @PutMapping("{idRole}")
    public ResponseEntity<Role> updateRole(@PathVariable("idRole") String idRole, @RequestBody Role role) {
        Role updatedRole = roleService.updateRole(idRole, role);
        return new ResponseEntity<>(updatedRole, HttpStatus.OK);
    }

    @DeleteMapping("/{idRole}")
    public ResponseEntity<Map<String, String>> deleteRole(@PathVariable("idRole") String idRole) {
        roleService.deleteRole(idRole);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Role deleted successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
