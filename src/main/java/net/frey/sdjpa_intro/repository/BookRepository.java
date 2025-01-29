package net.frey.sdjpa_intro.repository;

import net.frey.sdjpa_intro.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {}
