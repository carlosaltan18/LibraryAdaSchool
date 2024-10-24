package org.ada.biblioteca.repository.mongo.role;

import lombok.RequiredArgsConstructor;
import org.ada.biblioteca.bo.Role;
import org.ada.biblioteca.bo.mongo.RoleMongo;
import org.ada.biblioteca.repository.RoleRepository;
import org.ada.biblioteca.util.caster.RoleCaster;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Profile("mongo")
@RequiredArgsConstructor
@Repository
public class RoleRepositoryMongo implements RoleRepository {

    private final RoleRepositoryNoSql roleRepositoryNoSql;

    private final RoleCaster roleCaster;

    @Override
    public Role create(Role role) {
        RoleMongo roleMongo = roleCaster.roleToRoleMongo(role);
        RoleMongo newMongo = roleRepositoryNoSql.save(roleMongo);
        return roleCaster.roleMongoToRole(newMongo);
    }

    @Override
    public List<Role> getRoles() {
        return roleRepositoryNoSql.findAll().stream()
                .map(roleCaster::roleMongoToRole)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Role> findRoleById(String idRole) {
        Optional<RoleMongo> roleMongo = roleRepositoryNoSql.findById(idRole);
        return roleMongo.map(roleCaster::roleMongoToRole);
    }

    @Override
    public Role updateRole(Role role) {
        RoleMongo roleMongo = roleCaster.roleToRoleMongo(role);
        RoleMongo newMongo = roleRepositoryNoSql.save(roleMongo);
        return roleCaster.roleMongoToRole(newMongo);
    }

    @Override
    public void deleteRole(String idRole) {
        roleRepositoryNoSql.deleteById(idRole);
    }

    @Override
    public Optional<Role> findRoleByName(String roleName) {
        Optional<RoleMongo> roleMongo = roleRepositoryNoSql.findByRole(roleName);
        return roleMongo.map(roleCaster::roleMongoToRole);
    }
}
