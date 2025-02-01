package net.frey.sdjpa_intro

import net.frey.sdjpa_intro.repository.BookRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class SdjpaIntroApplicationTest extends Specification {
    @Autowired
    BookRepository bookRepository

    def "context loads"() {
        expect:
        true
    }

    def "test BookRepository"() {
        expect:
        bookRepository.count() > 0
    }
}
