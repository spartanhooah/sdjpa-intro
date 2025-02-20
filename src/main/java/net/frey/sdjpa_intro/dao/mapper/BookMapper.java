package net.frey.sdjpa_intro.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import net.frey.sdjpa_intro.entity.Book;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class BookMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        var book = new Book();
        book.setIsbn(rs.getString("isbn"));
        book.setId(rs.getLong("id"));
        book.setTitle(rs.getString("title"));
        book.setPublisher(rs.getString("publisher"));
        book.setAuthorId(rs.getLong("author_id"));

        return book;
    }
}
