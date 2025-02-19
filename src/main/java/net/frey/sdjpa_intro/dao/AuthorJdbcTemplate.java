package net.frey.sdjpa_intro.dao;

import lombok.RequiredArgsConstructor;
import net.frey.sdjpa_intro.entity.Author;
import net.frey.sdjpa_intro.mapper.AuthorMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthorJdbcTemplate {
    private final JdbcTemplate template;
    private final AuthorMapper mapper;

    public Author getById(Long id) {
        return template.queryForObject("SELECT * FROM author where id = ?", mapper, id);
    }

    public Author getByFirstAndLastName(String firstName, String lastName) {
        return template.queryForObject(
                "SELECT * FROM author WHERE first_name = ? and last_name = ?", mapper, firstName, lastName);
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
