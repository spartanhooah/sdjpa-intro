package net.frey.sdjpa_intro.repository;

import java.util.Optional;
import net.frey.sdjpa_intro.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByTitle(String title);

    Optional<Book> findByIsbn(String isbn);

    @Query("SELECT b FROM Book b where b.title = :title")
    Book findBookByTitleWithQuery(String title);

    @NativeQuery(value = "SELECT * FROM book WHERE TITLE = :title")
    Book findBookByTitleNativeQuery(String title);
}
