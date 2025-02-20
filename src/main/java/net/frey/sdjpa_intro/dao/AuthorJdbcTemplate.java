package net.frey.sdjpa_intro.dao;

import lombok.RequiredArgsConstructor;
import net.frey.sdjpa_intro.dao.mapper.AuthorMapper;
import net.frey.sdjpa_intro.entity.Author;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthorJdbcTemplate {
    private final JdbcTemplate template;
    private final AuthorMapper mapper;
    private final AuthorExtractor extractor;

    public Author getById(Long id) {
        var sql =
                """
                SELECT
                    author.id AS id,
                    first_name,
                    last_name,
                    book.id AS book_id,
                    book.isbn,
                    book.publisher,
                    book.title
                FROM
                    author
                LEFT OUTER JOIN
                    book
                    ON author.id = book.author_id
                WHERE author.id = ?""";

        return template.query(sql, extractor, id);
    }

    public Author getByFirstAndLastName(String firstName, String lastName) {
        var sql =
                """
                SELECT
                    author.id AS id,
                    first_name,
                    last_name,
                    book.id AS book_id,
                    book.isbn,
                    book.publisher,
                    book.title
                FROM
                    author
                LEFT OUTER JOIN
                    book
                    ON author.id = book.author_id
                WHERE author.first_name = ? AND author.last_name = ?""";

        return template.queryForObject(sql, mapper, firstName, lastName);
    }

    public Author saveAuthor(Author author) {
        template.update(
                "INSERT INTO author (first_name, last_name) VALUES (?, ?)",
                author.getFirstName(),
                author.getLastName());

        Long createdId = template.queryForObject("SELECT LAST_INSERT_ID()", Long.class);

        return getById(createdId);
    }

    public Author updateAuthor(Author author) {
        template.update(
                "UPDATE author SET first_name = ?, last_name = ? WHERE id = ?",
                author.getFirstName(),
                author.getLastName(),
                author.getId());

        return getById(author.getId());
    }

    public void deleteAuthor(Author author) {
        template.update("DELETE FROM author WHERE id = ?", author.getId());
    }
}
