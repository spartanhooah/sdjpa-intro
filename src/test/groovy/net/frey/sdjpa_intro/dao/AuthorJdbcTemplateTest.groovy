package net.frey.sdjpa_intro.dao

import net.frey.sdjpa_intro.entity.Author
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.dao.TransientDataAccessResourceException
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@ActiveProfiles("local")
@DataJpaTest
@ComponentScan(basePackages = ["net.frey.sdjpa_intro.dao"])
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AuthorJdbcTemplateTest extends Specification {
    @Autowired
    AuthorJdbcTemplate authorDao

    def "get author by ID"() {
        when:
        def author = authorDao.getById(1L)

        then:
        author
        author.books.size() == 3
    }

    def "get author by first and last name"() {
        when:
        def author = authorDao.getByFirstAndLastName("Craig", "Walls")

        then:
        author.firstName == "Craig"
    }

    def "save an author"() {
        given:
        def author = new Author(firstName: "John", lastName: "Thompson")

        when:
        def savedAuthor = authorDao.saveAuthor(author)

        then:
        savedAuthor.firstName == "John"
    }

    def "update an author"() {
        given:
        def author = new Author(firstName: "John", lastName: "Thompson")

        when:
        def saved = authorDao.saveAuthor(author)

        then:
        saved

        when:
        saved.lastName = "T"
        def updated = authorDao.updateAuthor(saved)

        then:
        updated.lastName == "T"
    }

    def "delete an author by ID"() {
        given:
        def author = new Author(firstName: "John", lastName: "Thompson")

        when:
        def saved = authorDao.saveAuthor(author)

        then:
        saved

        when:
        authorDao.deleteAuthor(saved)

        and:
        authorDao.getById(saved.id)

        then:
        thrown(TransientDataAccessResourceException)
    }
}
