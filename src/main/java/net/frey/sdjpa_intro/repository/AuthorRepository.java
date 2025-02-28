package net.frey.sdjpa_intro.repository;

import java.util.Optional;
import net.frey.sdjpa_intro.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByFirstNameAndLastName(String firstName, String lastName);
}
