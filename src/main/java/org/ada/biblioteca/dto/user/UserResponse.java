package org.ada.biblioteca.dto.user;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponse {
    private String id;
    private String name;
    private String username;
    private String email;
    private LocalDateTime dateCreation;
    private LocalDateTime dateUpdate;
}
