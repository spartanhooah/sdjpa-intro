package net.frey.sdjpa_intro.repository;

import net.frey.sdjpa_intro.entity.BookNatural;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookNaturalRepository extends JpaRepository<BookNatural, String> {}
