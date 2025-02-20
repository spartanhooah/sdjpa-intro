package net.frey.sdjpa_intro.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import lombok.RequiredArgsConstructor;
import net.frey.sdjpa_intro.dao.mapper.AuthorMapper;
import net.frey.sdjpa_intro.entity.Author;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthorExtractor implements ResultSetExtractor<Author> {
    private final AuthorMapper mapper;

    @Override
    public Author extractData(ResultSet rs) throws SQLException, DataAccessException {
        return mapper.mapRow(rs, 0);
    }
}
