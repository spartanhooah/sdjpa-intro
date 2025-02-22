package net.frey.sdjpa_intro

import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class SdjpaIntroApplicationTest extends Specification {
    def "context loads"() {
        expect:
        true
    }
}
