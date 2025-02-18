package net.frey.sdjpa_intro

import net.frey.sdjpa_intro.repository.BookRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import spock.lang.Specification

@DataJpaTest
//@ActiveProfiles("local")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MySQLIntegrationTest extends Specification {
    @Autowired
    BookRepository bookRepository

    def "test a transaction"() {
        when:
        def count = bookRepository.count()

        then:
        count == 5
    }
}
