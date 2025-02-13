package net.frey.sdjpa_intro.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import net.frey.sdjpa_intro.entity.Author;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthorDao {
    private final DataSource dataSource;

    public Author getById(Long id) {
        PreparedStatement statement;
        ResultSet resultSet = null;

        try (Connection connection = dataSource.getConnection()) {
            statement = connection.prepareStatement("SELECT * FROM author where id = ?");
            statement.setLong(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                var author = new Author();
                author.setId(id);
                author.setFirstName(resultSet.getString("first_name"));
                author.setLastName(resultSet.getString("last_name"));

                return author;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return null;
    }

    Author getByFirstAndLastName(String firstName, String lastName) {
        PreparedStatement statement;
        ResultSet resultSet = null;

        try (Connection connection = dataSource.getConnection()) {
            statement = connection.prepareStatement("SELECT * FROM author where first_name = ? and last_name = ?");
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                var author = new Author();
                author.setId(resultSet.getLong("id"));
                author.setFirstName(resultSet.getString("first_name"));
                author.setLastName(resultSet.getString("last_name"));

                return author;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return null;
    }
}
