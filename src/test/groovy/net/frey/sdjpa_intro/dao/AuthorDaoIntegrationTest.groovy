package net.frey.sdjpa_intro.dao

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
}
