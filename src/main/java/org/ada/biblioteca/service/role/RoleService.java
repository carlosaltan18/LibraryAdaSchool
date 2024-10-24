package org.ada.biblioteca.service.role;

import org.ada.biblioteca.bo.Role;
import org.ada.biblioteca.bo.postgres.RolePostgres;

import java.util.List;

public interface RoleService {
    Role createRole(Role role);
    List<Role> getRoles();
    Role findRoleById(String idRole);
    Role updateRole(String idRole, Role role);
    void deleteRole(String idRole);
}
