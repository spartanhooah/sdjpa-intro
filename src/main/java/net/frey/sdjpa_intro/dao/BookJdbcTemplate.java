package net.frey.sdjpa_intro.dao;

import lombok.RequiredArgsConstructor;
import net.frey.sdjpa_intro.entity.Book;
import net.frey.sdjpa_intro.mapper.BookMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookJdbcTemplate {
    private final JdbcTemplate template;
    private final BookMapper mapper;

    public Book getById(Long id) {
        return template.queryForObject("SELECT * FROM book where id = ?", mapper, id);
    }

    public Book getBookByTitle(String title) {
        return template.queryForObject("SELECT * FROM book WHERE title = ?", mapper, title);
    }

    public Book saveBook(Book book) {
        template.update(
                "INSERT INTO book (isbn, publisher, title, author_id) VALUES (?, ?, ?, ?)",
                book.getIsbn(),
                book.getPublisher(),
                book.getTitle(),
                book.getAuthorId());

        Long createdId = template.queryForObject("SELECT LAST_INSERT_ID()", Long.class);

        return getById(createdId);
    }

    public Book updateBook(Book book) {
        template.update(
                "UPDATE book SET isbn = ?, publisher = ?, title = ?, author_id = ? WHERE id = ?",
                book.getIsbn(),
                book.getPublisher(),
                book.getTitle(),
                book.getAuthorId(),
                book.getId());

        return getById(book.getId());
    }

    public void deleteBook(Book book) {
        template.update("DELETE FROM book WHERE id = ?", book.getId());
    }
}
