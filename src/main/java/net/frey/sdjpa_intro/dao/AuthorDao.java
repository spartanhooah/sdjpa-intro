package net.frey.sdjpa_intro.dao;

import lombok.RequiredArgsConstructor;
import net.frey.sdjpa_intro.entity.Author;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthorDao {
    public Author getById(Long id) {
        return null;
    }

    public Author getByFirstAndLastName(String firstName, String lastName) {
        return null;
    }

    public Author saveAuthor(Author author) {
        return null;
    }

    public Author updateAuthor(Author author) {
        return null;
    }

    public void deleteAuthorById(Long id) {}
}
