package org.ada.biblioteca.util.caster;

import org.ada.biblioteca.bo.Role;
import org.ada.biblioteca.bo.mongo.RoleMongo;
import org.ada.biblioteca.bo.postgres.RolePostgres;
import org.springframework.stereotype.Component;

@Component
public class RoleCaster {
    public RolePostgres roleToRolePostgres(Role role) {
        RolePostgres rolePostgres = new RolePostgres();
        rolePostgres.setIdRole((role.getIdRole() != null && !role.getIdRole().isEmpty()) ? Long.parseLong(role.getIdRole()) : null);
        rolePostgres.setRole(role.getRole());
        return rolePostgres;
    }

    public Role rolePostgresToRole(RolePostgres rolePostgres) {
        Role role = new Role();
        role.setIdRole(rolePostgres.getIdRole().toString());
        role.setRole(rolePostgres.getRole());
        return role;
    }

    public RoleMongo roleToRoleMongo(Role role) {
        RoleMongo roleMongo = new RoleMongo();
        roleMongo.setIdRole(role.getIdRole());
        roleMongo.setRole(role.getRole());
        return roleMongo;
    }

    public Role roleMongoToRole(RoleMongo roleMongo) {
        Role role = new Role();
        role.setIdRole(roleMongo.getIdRole());
        role.setRole(roleMongo.getRole());
        return role;
    }
}
