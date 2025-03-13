package net.frey.sdjpa_intro.dao;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.List;
import net.frey.sdjpa_intro.dao.mapper.AuthorMapper;
import net.frey.sdjpa_intro.entity.Author;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("local")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackages = {"net.frey.sdjpa_intro.dao"})
public class JTAuthorTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    AuthorExtractor authorExtractor;

    @Autowired
    AuthorMapper authorMapper;

    AuthorJdbcTemplate authorDao;

    @BeforeEach
    void setUp() {
        authorDao = new AuthorJdbcTemplate(jdbcTemplate, authorMapper, authorExtractor);
    }

    @Test
    void findAllAuthorsByLastName() {
        List<Author> authors = authorDao.getByLastName("Smith", PageRequest.of(0, 10));

        assertThat(authors).isNotNull();
        assertThat(authors.size()).isEqualTo(10);
    }
}
