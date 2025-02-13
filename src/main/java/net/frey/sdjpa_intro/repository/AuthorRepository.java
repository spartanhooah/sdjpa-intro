package net.frey.sdjpa_intro.repository;

import net.frey.sdjpa_intro.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {}
