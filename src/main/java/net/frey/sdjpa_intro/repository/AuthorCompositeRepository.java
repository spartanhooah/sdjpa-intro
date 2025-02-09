package net.frey.sdjpa_intro.repository;

import net.frey.sdjpa_intro.entity.composite.AuthorComposite;
import net.frey.sdjpa_intro.entity.composite.NameId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorCompositeRepository extends JpaRepository<AuthorComposite, NameId> {}
