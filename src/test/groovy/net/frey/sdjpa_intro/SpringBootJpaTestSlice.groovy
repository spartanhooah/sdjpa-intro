package net.frey.sdjpa_intro

import net.frey.sdjpa_intro.entity.Book
import net.frey.sdjpa_intro.repository.BookRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.annotation.Commit
import spock.lang.Specification
import spock.lang.Stepwise

@Stepwise
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SpringBootJpaTestSlice extends Specification {
    @Autowired
    BookRepository bookRepository

    @Commit
    def "test JPA test slice"() {
        given:
        def countBefore = bookRepository.count()
        assert countBefore == 5

        when:
        bookRepository.save(new Book())

        then:
        bookRepository.count() > countBefore
    }

    def "test a transaction"() {
        when:
        def count = bookRepository.count()

        then:
        count == 6
    }
}
