package net.frey.sdjpa_intro.bootstrap;

import lombok.RequiredArgsConstructor;
import net.frey.sdjpa_intro.entity.Book;
import net.frey.sdjpa_intro.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final BookRepository bookRepository;

    @Override
    public void run(String... args) throws Exception {
        var book1 = new Book("Title One", "12345-435789", "Some Author");
        System.out.println("ID of book 1 before saving: " + book1.getId());
        var savedBook1 = bookRepository.save(book1);


        var book2 = new Book("Title Two", "098-32418", "Another Author");
        var savedBook2 = bookRepository.save(book2);

        bookRepository.findAll().forEach(book -> {
            System.out.println("Book ID: " + book.getId());
            System.out.println("Book Title: " + book.getTitle());
        });
    }
}
