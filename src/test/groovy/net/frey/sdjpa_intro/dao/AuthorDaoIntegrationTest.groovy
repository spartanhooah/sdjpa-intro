package net.frey.sdjpa_intro.dao

import jakarta.persistence.EntityNotFoundException
import net.frey.sdjpa_intro.entity.Author
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@ActiveProfiles("local")
@DataJpaTest
@ComponentScan(basePackages = ["net.frey.sdjpa_intro.dao"])
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AuthorDaoIntegrationTest extends Specification {
    @Autowired
    AuthorDao authorDao

    def "get author"() {
        when:
        def author = authorDao.getById(1L)

        then:
        author
    }

    def "get author by first and last name"() {
        when:
        def author = authorDao.getByFirstAndLastName("Craig", "Walls")

        then:
        author.firstName == "Craig"
    }

    def "get author by name but doesn't exist"() {
        when:
        authorDao.getByFirstAndLastName("foo", "bar")

        then:
        thrown(EntityNotFoundException)
    }

    def "save an author"() {
        given:
        def author = new Author(firstName: "John", lastName: "Thompson")

        when:
        def savedAuthor = authorDao.saveAuthor(author)

        then:
        savedAuthor.firstName == "John"
        savedAuthor.id
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
        authorDao.deleteAuthorById(saved.id)

        and:
        authorDao.getById(saved.id)

        then:
        thrown(JpaObjectRetrievalFailureException)
    }

    def "get all authors by last name"() {
        when:
        def result = authorDao.getAllByLastName("Smith", PageRequest.of(0, 10))

        then:
        result.size() == 10
    }

    def "get all authors by last name sort by first name ascending"() {
        when:
        def result = authorDao.getAllByLastName("Smith", PageRequest.of(0, 10, Sort.by(Sort.Order.asc("firstName"))))

        then:
        result.size() == 10
        result[0].firstName == "Ahmed"
    }

    def "get all authors by last name sort by first name descending"() {
        when:
        def result = authorDao.getAllByLastName("Smith", PageRequest.of(0, 10, Sort.by(Sort.Order.desc("firstName"))))

        then:
        result.size() == 10
        result[0].firstName == "Yugal"
    }
}
