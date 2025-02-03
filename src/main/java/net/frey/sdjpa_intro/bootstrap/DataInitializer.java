package net.frey.sdjpa_intro.bootstrap;

import lombok.RequiredArgsConstructor;
import net.frey.sdjpa_intro.entity.Book;
import net.frey.sdjpa_intro.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile({"local", "default"})
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final BookRepository bookRepository;

    @Override
    public void run(String... args) {
        bookRepository.deleteAll();

        var book1 = new Book("Title One", "12345-435789", "Some Author");
        var savedBook1 = bookRepository.save(book1);


        var book2 = new Book("Title Two", "098-32418", "Another Author");
        var savedBook2 = bookRepository.save(book2);
    }
}
