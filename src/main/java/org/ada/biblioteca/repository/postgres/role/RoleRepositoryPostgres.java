package org.ada.biblioteca.repository.postgres.role;

import lombok.RequiredArgsConstructor;
import org.ada.biblioteca.bo.Role;
import org.ada.biblioteca.bo.postgres.RolePostgres;
import org.ada.biblioteca.repository.RoleRepository;
import org.ada.biblioteca.util.caster.RoleCaster;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Profile("postgres")
@RequiredArgsConstructor
@Repository
public class RoleRepositoryPostgres implements RoleRepository {

    private final RoleRepositoryJpa roleRepositoryJpa;
    private final RoleCaster roleCaster;

    @Override
    public Role create(Role role) {
        RolePostgres rolePostgres = roleCaster.roleToRolePostgres(role);
        RolePostgres newRole = roleRepositoryJpa.save(rolePostgres);
        return roleCaster.rolePostgresToRole(newRole);
    }

    @Override
    public List<Role> getRoles() {
        return roleRepositoryJpa.findAll().stream()
                .map(roleCaster::rolePostgresToRole)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Role> findRoleById(String idRole) {
        Optional<RolePostgres> rolePostgres = roleRepositoryJpa.findById(Long.parseLong(idRole));
        return rolePostgres.map(roleCaster::rolePostgresToRole);
    }

    @Override
    public Role updateRole(Role role) {
        RolePostgres rolePostgres = roleCaster.roleToRolePostgres(role);
        RolePostgres newRole = roleRepositoryJpa.save(rolePostgres);
        return roleCaster.rolePostgresToRole(newRole);
    }

    @Override
    public void deleteRole(String idRole) {
        roleRepositoryJpa.deleteById(Long.parseLong(idRole));
    }

    @Override
    public Optional<Role> findRoleByName(String roleName) {
        Optional<RolePostgres> rolePostgres = roleRepositoryJpa.findByRole(roleName);
        return rolePostgres.map(roleCaster::rolePostgresToRole);
    }
}
