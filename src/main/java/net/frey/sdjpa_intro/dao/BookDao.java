package net.frey.sdjpa_intro.dao;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import net.frey.sdjpa_intro.entity.Book;
import net.frey.sdjpa_intro.repository.BookRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookDao {
    private final BookRepository repository;

    public Book getById(Long id) {
        return repository.getReferenceById(id);
    }

    public Book getByIsbn(String isbn) {
        return repository.findByIsbn(isbn).orElseThrow(EntityNotFoundException::new);
    }

    public Book getBookByTitle(String title) {
        return repository.findByTitle(title).orElseThrow(EntityNotFoundException::new);
    }

    public Book saveBook(Book book) {
        return repository.save(book);
    }

    @Transactional
    public Book updateBook(Book book) {
        var found = getById(book.getId());

        found.setTitle(book.getTitle());
        found.setAuthorId(book.getAuthorId());
        found.setPublisher(book.getPublisher());
        found.setIsbn(book.getIsbn());

        return saveBook(book);
    }

    public void deleteBookById(Long id) {
        repository.deleteById(id);
    }

    public List<Book> getAll() {
        return repository.findAll();
    }

    public List<Book> getAll(Pageable pageable) {
        return repository.findAll(pageable).getContent();
    }

    public List<Book> getAll(int pageSize, int offset) {
        var pageable = PageRequest.ofSize(pageSize);

        if (offset > 0) {
            pageable = pageable.withPage(offset / pageSize);
        } else {
            pageable = pageable.withPage(0);
        }

        return getAll(pageable);
    }

    public List<Book> getAllSortByTitle(Pageable pageable) {
        var bookPage = repository.findAll(pageable);

        return bookPage.getContent();
    }
}
