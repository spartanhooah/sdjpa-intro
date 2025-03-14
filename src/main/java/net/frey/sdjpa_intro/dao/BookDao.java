package net.frey.sdjpa_intro.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import net.frey.sdjpa_intro.entity.Book;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookDao {
    private final EntityManagerFactory emf;

    public List<Book> getAll() {
        try (var em = getEntityManager()) {
            var query = em.createNamedQuery("book_find_all", Book.class);

            return query.getResultList();
        }
    }

    public Book getById(Long id) {
        try (var em = getEntityManager()) {
            return em.find(Book.class, id);
        }
    }

    public Book getByIsbn(String isbn) {
        try (var em = getEntityManager()) {
            var query = em.createQuery("SELECT b FROM Book b WHERE b.isbn = :isbn", Book.class);
            query.setParameter("isbn", isbn);

            return query.getSingleResult();
        }
    }

    public Book getBookByTitle(String title) {
        try (var em = getEntityManager()) {
            var query = em.createNamedQuery("find_by_title", Book.class);
            query.setParameter("title", title);

            return query.getSingleResult();
        }
    }

    public Book getByTitleCriteria(String title) {
        try (var em = getEntityManager()) {
            var builder = em.getCriteriaBuilder();
            var query = builder.createQuery(Book.class);
            var root = query.from(Book.class);

            var titleParam = builder.parameter(String.class);

            var titlePredicate = builder.equal(root.get("title"), titleParam);

            query.select(root).where(titlePredicate);

            var typedQuery = em.createQuery(query);
            typedQuery.setParameter(titleParam, title);

            return typedQuery.getSingleResult();
        }
    }

    public Book getByTitleNative(String title) {
        try (var em = getEntityManager()) {
            var query = em.createNativeQuery("SELECT * FROM book b WHERE b.title = :title", Book.class);
            query.setParameter("title", title);

            return (Book) query.getSingleResult();
        }
    }

    public Book saveBook(Book book) {
        try (var em = getEntityManager()) {
            em.getTransaction().begin();
            em.persist(book);
            em.getTransaction().commit();

            return book;
        }
    }

    public Book updateBook(Book book) {
        try (var em = getEntityManager()) {
            em.joinTransaction();
            em.merge(book);
            em.flush();
            em.getTransaction().commit();

            return book;
        }
    }

    public void deleteBookById(Long id) {
        try (var em = getEntityManager()) {
            em.getTransaction().begin();
            var book = em.find(Book.class, id);
            em.remove(book);
            em.getTransaction().commit();
        }
    }

    public List<Book> getAll(Pageable pageable) {
        try (var em = getEntityManager()) {
            var query = em.createQuery("SELECT b FROM Book b", Book.class);
            query.setFirstResult(Math.toIntExact(pageable.getOffset()));
            query.setMaxResults(pageable.getPageSize());

            return query.getResultList();
        }
    }

    public List<Book> getAllBooksSortByTitle(Pageable pageable) {
        try (var em = getEntityManager()) {
            String hql = "SELECT b FROM Book b ORDER BY b.title "
                    + pageable.getSort().getOrderFor("title").getDirection().name();

            var query = em.createQuery(hql, Book.class);
            query.setFirstResult(Math.toIntExact(pageable.getOffset()));
            query.setMaxResults(pageable.getPageSize());

            return query.getResultList();
        }
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
