package org.ada.biblioteca.service.role;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.ada.biblioteca.bo.Role;
import org.ada.biblioteca.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    private static final String INVALID_ROLE_ID = "Invalid idRole format for Postgres: ";

    @Value("${spring.profiles.active}")
    private String profile;

    @Override
    public Role createRole(Role role) {
        return roleRepository.create(role);
    }

    @Override
    public List<Role> getRoles() {
        return roleRepository.getRoles();
    }

    @Override
    public Role findRoleById(String idRole) {
        if(profile.equals("postgres")) {
            validateIsNumeric(idRole, INVALID_ROLE_ID + idRole);
        }
        return roleRepository.findRoleById(idRole)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with id " + idRole));
    }

    @Override
    public Role updateRole(String idRole, Role role) {
        if(profile.equals("postgres")) {
            validateIsNumeric(idRole, INVALID_ROLE_ID + idRole);
        }
        Role roleFound = roleRepository.findRoleById(idRole)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with id " + idRole));
        roleFound.setRole(role.getRole());
        return roleRepository.updateRole(roleFound);
    }

    @Override
    public void deleteRole(String idRole) {
        if(profile.equals("postgres")) {
            validateIsNumeric(idRole, INVALID_ROLE_ID + idRole);
        }
        roleRepository.findRoleById(idRole)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with id " + idRole));
        roleRepository.deleteRole(idRole);
    }

    private void validateIsNumeric(String value, String errorMessage) {
        if (!isNumeric(value)) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    private boolean isNumeric(String value) {
        return Optional.ofNullable(value)
                .filter(v -> v.matches("\\d+"))
                .isPresent();
    }
}
