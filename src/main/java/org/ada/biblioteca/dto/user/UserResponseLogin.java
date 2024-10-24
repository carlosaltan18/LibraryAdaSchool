package org.ada.biblioteca.dto.user;

import lombok.Data;

@Data
public class UserResponseLogin {
    private String token;
    private long expiresIn;
}
