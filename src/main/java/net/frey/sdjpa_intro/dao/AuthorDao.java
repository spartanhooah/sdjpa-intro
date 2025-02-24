package net.frey.sdjpa_intro.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import net.frey.sdjpa_intro.entity.Author;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthorDao {
    private final EntityManagerFactory emf;

    public List<Author> getAll() {
        try (var em = getEntityManager()) {
            var query = em.createNamedQuery("author_find_all", Author.class);

            return query.getResultList();
        }
    }

    public Author getById(Long id) {
        var em = getEntityManager();

        var author = em.find(Author.class, id);
        em.close();

        return author;
    }

    Author getByFirstAndLastName(String firstName, String lastName) {
        var em = getEntityManager();
        var query = em.createQuery(
                "SELECT a FROM Author a WHERE a.firstName = :first_name " + "AND a.lastName = :last_name",
                Author.class);
        query.setParameter("first_name", firstName);
        query.setParameter("last_name", lastName);

        Author author = query.getSingleResult();
        em.close();

        return author;
    }

    List<Author> authorByLastNameLike(String lastName) {
        try (var em = getEntityManager()) {
            var query = em.createQuery("SELECT a FROM Author a where a.lastName LIKE :last_name", Author.class);
            query.setParameter("last_name", "%" + lastName + "%");

            return query.getResultList();
        }
    }

    public Author saveAuthor(Author author) {
        var em = getEntityManager();

        // might only work in the test context
        //        em.joinTransaction();

        // doesn't "rely" on a transaction already existing (e.g. from the Spring Boot test)
        // however, this means the test executions will save data to the DB
        em.getTransaction().begin();
        em.persist(author);
        //        em.flush();
        em.getTransaction().commit();
        em.close();

        return author;
    }

    public Author updateAuthor(Author author) {
        var em = getEntityManager();

        // see comment in save method
        em.joinTransaction();
        em.merge(author);
        em.flush();
        em.getTransaction().commit();
        em.close();

        return author;
    }

    public void deleteAuthorById(Long id) {
        var em = getEntityManager();

        em.getTransaction().begin();
        var author = em.find(Author.class, id);
        em.remove(author);
        em.getTransaction().commit();

        em.close();
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
