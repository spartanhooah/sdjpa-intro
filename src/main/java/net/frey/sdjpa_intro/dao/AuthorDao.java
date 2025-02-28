package net.frey.sdjpa_intro.dao;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.frey.sdjpa_intro.entity.Author;
import net.frey.sdjpa_intro.repository.AuthorRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthorDao {
    private final AuthorRepository repository;

    public Author getById(Long id) {
        return repository.getReferenceById(id);
    }

    public Author getByFirstAndLastName(String firstName, String lastName) {
        return repository.findByFirstNameAndLastName(firstName, lastName).orElseThrow(EntityNotFoundException::new);
    }

    public Author saveAuthor(Author author) {
        return repository.save(author);
    }

    @Transactional
    public Author updateAuthor(Author author) {
        var found = getById(author.getId());

        found.setFirstName(author.getFirstName());
        found.setLastName(author.getLastName());

        return saveAuthor(found);
    }

    public void deleteAuthorById(Long id) {
        repository.deleteById(id);
    }
}
