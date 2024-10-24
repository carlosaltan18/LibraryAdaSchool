package org.ada.biblioteca.repository;

import org.ada.biblioteca.bo.Role;
import org.ada.biblioteca.bo.postgres.RolePostgres;

import java.util.List;
import java.util.Optional;

public interface RoleRepository {
    Role create(Role role);
    List<Role> getRoles();
    Optional<Role> findRoleById(String idRole);
    Role updateRole(Role role);
    void deleteRole(String idRole);
    Optional<Role> findRoleByName(String roleName);
}
