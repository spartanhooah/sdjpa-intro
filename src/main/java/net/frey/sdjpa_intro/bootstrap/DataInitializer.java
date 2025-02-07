package net.frey.sdjpa_intro.bootstrap;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.frey.sdjpa_intro.entity.AuthorUuid;
import net.frey.sdjpa_intro.entity.Book;
import net.frey.sdjpa_intro.entity.BookUuid;
import net.frey.sdjpa_intro.repository.AuthorUuidRepository;
import net.frey.sdjpa_intro.repository.BookRepository;
import net.frey.sdjpa_intro.repository.BookUuidRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile({"local", "default"})
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final BookRepository bookRepository;
    private final AuthorUuidRepository authorUUIDRepository;
    private final BookUuidRepository bookUuidRepository;

    @Override
    public void run(String... args) {
        bookRepository.deleteAll();

        var book1 = new Book("Title One", "12345-435789", "O'Reilly");
        bookRepository.save(book1);

        var book2 = new Book("Title Two", "098-32418", "MIT Press");
        bookRepository.save(book2);

        bookRepository.findAll().forEach(book -> log.info("Book {} has ID: {}", book.getTitle(), book.getId()));

        AuthorUuid authorUuid = new AuthorUuid();
        authorUuid.setFirstName("Brandon");
        authorUuid.setLastName("Sanderson");
        var savedAuthor = authorUUIDRepository.save(authorUuid);
        log.info("Author ID: {}", savedAuthor.getId());

        var bookUuid = new BookUuid();
        bookUuid.setTitle("Thud!");
        var savedBookUuid = bookUuidRepository.save(bookUuid);
        log.info("Saved book UUID: {}", savedBookUuid.getId());
    }
}
