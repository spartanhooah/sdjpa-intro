package net.frey.sdjpa_intro.repository;

import net.frey.sdjpa_intro.entity.composite.AuthorEmbedded;
import net.frey.sdjpa_intro.entity.composite.NameId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorEmbeddedRepository extends JpaRepository<AuthorEmbedded, NameId> {}
