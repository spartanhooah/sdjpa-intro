package net.frey.sdjpa_intro.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import net.frey.sdjpa_intro.entity.Book;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookDao {
    private final EntityManagerFactory emf;

    public Book getById(Long id) {
        var em = getEntityManager();

        var book = em.find(Book.class, id);
        em.close();

        return book;
    }

    public Book getBookByTitle(String title) {
        var em = getEntityManager();
        var query = em.createQuery("SELECT b FROM Book b WHERE b.title = :title", Book.class);
        query.setParameter("title", title);

        var book = query.getSingleResult();
        em.close();

        return book;
    }

    public Book saveBook(Book book) {
        var em = getEntityManager();

        em.getTransaction().begin();
        em.persist(book);
        em.getTransaction().commit();
        em.close();

        return book;
    }

    public Book updateBook(Book book) {
        var em = getEntityManager();

        em.joinTransaction();
        em.merge(book);
        em.flush();
        em.getTransaction().commit();
        em.close();

        return book;
    }

    public void deleteBookById(Long id) {
        var em = getEntityManager();

        em.getTransaction().begin();
        var book = em.find(Book.class, id);
        em.remove(book);
        em.getTransaction().commit();
        em.close();
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
