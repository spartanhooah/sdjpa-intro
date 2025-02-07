package net.frey.sdjpa_intro.repository;

import java.util.UUID;
import net.frey.sdjpa_intro.entity.BookUuid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookUuidRepository extends JpaRepository<BookUuid, UUID> {}
