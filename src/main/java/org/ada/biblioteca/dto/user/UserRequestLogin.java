package org.ada.biblioteca.dto.user;

import lombok.Data;

@Data
public class UserRequestLogin {
    private String username;
    private String password;
}
