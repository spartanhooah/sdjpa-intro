package net.frey.sdjpa_intro.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import net.frey.sdjpa_intro.entity.Author;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

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

    public Author getByFirstAndLastName(String firstName, String lastName) {
        var em = getEntityManager();

        var query = em.createNamedQuery("find_by_name", Author.class);
        query.setParameter("first_name", firstName);
        query.setParameter("last_name", lastName);

        Author author = query.getSingleResult();
        em.close();

        return author;
    }

    public Author getAuthorByNameCriteria(String firstName, String lastName) {
        try (var em = getEntityManager()) {
            var builder = em.getCriteriaBuilder();
            var query = builder.createQuery(Author.class);
            var root = query.from(Author.class);

            var firstParam = builder.parameter(String.class);
            var lastParam = builder.parameter(String.class);

            var firstPredicate = builder.equal(root.get("firstName"), firstParam);
            var lastPredicate = builder.equal(root.get("lastName"), lastParam);

            query.select(root).where(builder.and(firstPredicate, lastPredicate));

            var typedQuery = em.createQuery(query);
            typedQuery.setParameter(firstParam, firstName);
            typedQuery.setParameter(lastParam, lastName);

            return typedQuery.getSingleResult();
        }
    }

    public Author getAuthorByNameNative(String firstName, String lastName) {
        try (var em = getEntityManager()) {
            var query = em.createNativeQuery(
                    "SELECT * FROM author a WHERE a.first_name = ? AND a.last_name = ?", Author.class);
            query.setParameter(1, firstName);
            query.setParameter(2, lastName);

            return (Author) query.getSingleResult();
        }
    }

    public List<Author> getAuthorByLastNameLike(String lastName) {
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

    public List<Author> getAll(Pageable pageable) {
        try (var em = getEntityManager()) {
            var query = em.createQuery("SELECT a FROM Author a", Author.class);
            query.setFirstResult(Math.toIntExact(pageable.getOffset()));
            query.setMaxResults(pageable.getPageSize());

            return query.getResultList();
        }
    }

    public List<Author> getAllByLastName(String lastName, Pageable pageable) {
        try (var em = getEntityManager()) {
            var hql = "SELECT a FROM Author a WHERE a.lastName = :lastName";

            Sort.Order firstNameSorting = pageable.getSort().getOrderFor("first_name");

            if (firstNameSorting != null) {
                hql += " ORDER BY firstName " + firstNameSorting.getDirection().name();
            }

            var query = em.createQuery(hql, Author.class);
            query.setParameter("lastName", lastName);
            query.setFirstResult(Math.toIntExact(pageable.getOffset()));
            query.setMaxResults(pageable.getPageSize());

            return query.getResultList();
        }
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
