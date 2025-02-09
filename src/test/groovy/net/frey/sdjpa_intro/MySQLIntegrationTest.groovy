package net.frey.sdjpa_intro

import net.frey.sdjpa_intro.entity.AuthorUuid
import net.frey.sdjpa_intro.entity.BookNatural
import net.frey.sdjpa_intro.entity.BookUuid
import net.frey.sdjpa_intro.repository.AuthorUuidRepository
import net.frey.sdjpa_intro.repository.BookNaturalRepository
import net.frey.sdjpa_intro.repository.BookRepository
import net.frey.sdjpa_intro.repository.BookUuidRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.ComponentScan
import spock.lang.Specification

@DataJpaTest
//@ActiveProfiles("local")
@ComponentScan(basePackages = ['net.frey.sdjpa_intro.bootstrap'])
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MySQLIntegrationTest extends Specification {
    @Autowired
    BookRepository bookRepository

    @Autowired
    AuthorUuidRepository authorUuidRepository

    @Autowired
    BookUuidRepository bookUuidRepository

    @Autowired
    BookNaturalRepository bookNaturalRepository

    def "test a transaction"() {
        when:
        def count = bookRepository.count()

        then:
        count == 2
    }

    def "save and retrieve an AuthorUuid"() {
        given:
        def author = new AuthorUuid(firstName: "Nora", lastName: "Jemisin")

        when:
        def savedAuthor = authorUuidRepository.save(author)

        then:
        authorUuidRepository.count() == 2

        and:
        when:
        def foundAuthor = authorUuidRepository.findById(savedAuthor.id).get()

        then:
        foundAuthor.lastName == "Jemisin"
    }

    def "save and retrieve a BookUuid"() {
        given:
        def book = new BookUuid(title: "The Fifth Season")

        when:
        def savedBook = bookUuidRepository.save(book)

        then:
        bookUuidRepository.count() == 2

        and:
        when:
        def foundBook = bookUuidRepository.findById(savedBook.id).get()

        then:
        foundBook.title == "The Fifth Season"
    }

    def "test BookNatural"() {
        given:
        def book = new BookNatural(title: "My Book")

        when:
        def savedBook = bookNaturalRepository.save(book)
        def foundBook = bookNaturalRepository.getReferenceById(savedBook.title)

        then:
        foundBook
    }
}
