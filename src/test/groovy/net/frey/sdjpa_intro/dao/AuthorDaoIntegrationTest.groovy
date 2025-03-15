package net.frey.sdjpa_intro.dao

import net.frey.sdjpa_intro.entity.Author
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
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

        then:
        !authorDao.getById(saved.id)
    }

    def "get list of authors"() {
        when:
        def authors = authorDao.getAuthorByLastNameLike("Wall")

        then:
        authors
        authors.size() > 0
    }

    def "find all authors"() {
        expect:
        authorDao.getAll().size() > 0
    }

    def "get author with criteria"() {
        expect:
        authorDao.getAuthorByNameCriteria("Craig", "Walls")
    }

    def "get author by name with native query"() {
        expect:
        authorDao.getAuthorByNameNative("Craig", "Walls")
    }

    def "get all using paging"() {
        when:
        def result = authorDao.getAll(PageRequest.of(0, 10))

        then:
        result.size() == 10
    }

    def "get all authors by last name with paging and sorting"() {
        when:
        def result = authorDao.getAllByLastName('Smith',
            PageRequest.of(0, 10, Sort.by(Sort.Order.desc("first_name"))))

        then:
        result.size() == 10
        result[0].firstName == 'Yugal'
    }
}
