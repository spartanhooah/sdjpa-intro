package net.frey.sdjpa_intro.entity.composite;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.Data;

@Data
@Entity
@IdClass(NameId.class)
public class AuthorComposite {
    @Id
    private String firstName;

    @Id
    private String lastName;

    private String country;
}
