package net.frey.sdjpa_intro.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@ActiveProfiles("local")
@DataJpaTest
@ComponentScan(basePackages = ["net.frey.jdbc.repository"])
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookRepositoryTest extends Specification {
    @Autowired
    BookRepository repository

    def "find by title using query annotation"() {
        expect:
        repository.findBookByTitleWithQuery("Clean Code")
    }

    def "find by title using query annotation"() {
        expect:
        repository.findBookByTitleNativeQuery("Clean Code")
    }
}
