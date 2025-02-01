package net.frey.sdjpa_intro

import net.frey.sdjpa_intro.entity.Book
import net.frey.sdjpa_intro.repository.BookRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.annotation.Commit
import spock.lang.Specification
import spock.lang.Stepwise

@Stepwise
@DataJpaTest
@ComponentScan(basePackages = ['net.frey.sdjpa_intro.bootstrap'])
class SpringBootJpaTestSlice extends Specification {
    @Autowired
    BookRepository bookRepository

    @Commit
    def "test JPA test slice"() {
        given:
        def countBefore = bookRepository.count()
        assert countBefore == 1

        when:
        bookRepository.save(new Book())

        then:
        bookRepository.count() > countBefore
    }

    def "test a transaction"() {
        when:
        def count = bookRepository.count()

        then:
        count == 3
    }
}
