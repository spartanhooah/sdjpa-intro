package net.frey.sdjpa_intro.dao

import net.frey.sdjpa_intro.entity.Book
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@ActiveProfiles("local")
@DataJpaTest
@ComponentScan(basePackages = ["net.frey.sdjpa_intro.dao"])
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookDaoIntegrationTest extends Specification {
    @Autowired
    BookDao bookDao

    def "get book by ID"() {
        when:
        def book = bookDao.getById(1L)

        then:
        book
    }

    def "get book by title"() {
        when:
        def book = bookDao.getBookByTitle("Clean Code")

        then:
        book.isbn == "978-0134494166"
    }

    def "save a book"() {
        given:
        def book = new Book(title: "Arcanum Unbounded", isbn: "978-0-7653-9118-6", publisher: "TOR Fantasy")

        when:
        def savedBook = bookDao.saveBook(book)

        then:
        savedBook.title == "Arcanum Unbounded"
    }

    def "update a book"() {
        given:
        def book = new Book(title: "Arcanum Unbounded", isbn: "978-0-7653-9118-6", publisher: "TOR Fantasy")

        when:
        def saved = bookDao.saveBook(book)

        then:
        saved

        when:
        saved.title = "What If?"
        def updated = bookDao.updateBook(saved)

        then:
        updated.title == "What If?"
    }

    def "delete a book by ID"() {
        given:
        def book = new Book(title: "Arcanum Unbounded", isbn: "978-0-7653-9118-6", publisher: "TOR Fantasy")

        when:
        def saved = bookDao.saveBook(book)

        then:
        saved

        when:
        bookDao.deleteBookById(saved.id)

        then:
        !bookDao.getById(saved.id)
    }
}
