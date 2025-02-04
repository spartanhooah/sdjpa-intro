package net.frey.sdjpa_intro.repository;

import java.util.UUID;
import net.frey.sdjpa_intro.entity.AuthorUuid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorUuidRepository extends JpaRepository<AuthorUuid, UUID> {}
