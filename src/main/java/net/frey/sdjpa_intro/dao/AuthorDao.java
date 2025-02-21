package net.frey.sdjpa_intro.dao;

import java.sql.ResultSet;
import lombok.RequiredArgsConstructor;
import net.frey.sdjpa_intro.entity.Author;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthorDao {
    public Author getById(Long id) {
        return null;
    }

    Author getByFirstAndLastName(String firstName, String lastName) {
        return null;
    }

    public Author saveAuthor(Author author) {
        return null;
    }

    public Author updateAuthor(Author author) {
        return null;
    }

    public void deleteAuthor(Author author) {}

    private static Author constructAuthor(ResultSet resultSet) {
        return null;
    }
}
