package org.ada.biblioteca.dto.user;

import lombok.Data;

@Data
public class UserRequestUpdate {
    private String name;
    private String username;
    private String email;
}
