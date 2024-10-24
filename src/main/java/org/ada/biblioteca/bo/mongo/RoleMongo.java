package org.ada.biblioteca.bo.mongo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "roles")
public class RoleMongo {
    @Id
    private String idRole;
    private String role;
}
