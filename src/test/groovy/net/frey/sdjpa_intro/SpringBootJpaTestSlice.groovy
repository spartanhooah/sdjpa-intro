package net.frey.sdjpa_intro

import net.frey.sdjpa_intro.dao.BookJdbcTemplate
import net.frey.sdjpa_intro.entity.Book
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
    BookJdbcTemplate bookTemplate

    @Commit
    def "test JPA test slice"() {
        given:
        def countBefore = bookTemplate.count()
        assert countBefore == 5

        when:
        bookTemplate.save(new Book())

        then:
        bookTemplate.count() > countBefore
    }

    def "test a transaction"() {
        when:
        def count = bookTemplate.count()

        then:
        count == 6
    }
}
