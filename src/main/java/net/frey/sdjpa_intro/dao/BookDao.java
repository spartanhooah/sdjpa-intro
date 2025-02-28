package net.frey.sdjpa_intro.dao;

import lombok.RequiredArgsConstructor;
import net.frey.sdjpa_intro.entity.Book;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookDao {
    public Book getById(Long id) {
        return null;
    }

    public Book getByIsbn(String isbn) {
        return null;
    }

    public Book getBookByTitle(String title) {
        return null;
    }

    public Book saveBook(Book book) {
        return null;
    }

    public Book updateBook(Book book) {
        return null;
    }

    public void deleteBookById(Long id) {}
}
