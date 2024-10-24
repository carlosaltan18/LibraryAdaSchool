package org.ada.biblioteca.bo.postgres;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class LoanPostgresId implements Serializable {
    private Long idBook;
    private Long idUser;
}
