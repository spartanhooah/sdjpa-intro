package net.frey.sdjpa_intro.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class BookNatural {
    @Id
    private String title;

    private String isbn;
    private String publisher;
}
