package net.frey.sdjpa_intro.entity.composite;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class NameId implements Serializable {
    private String firstName;
    private String lastName;
}
