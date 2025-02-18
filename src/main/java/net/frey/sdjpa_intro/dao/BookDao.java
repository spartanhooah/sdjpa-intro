package net.frey.sdjpa_intro.dao;

import lombok.RequiredArgsConstructor;
import net.frey.sdjpa_intro.entity.Book;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Component
@RequiredArgsConstructor
public class BookDao {
    private final DataSource dataSource;

    public Book getById(Long id) {
        ResultSet resultSet = null;

        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM book where id = ?")) {
            statement.setLong(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return constructBook(resultSet);
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

    public Book getBookByTitle(String title) {
        ResultSet resultSet = null;

        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM book where title = ?")) {
            statement.setString(1, title);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return constructBook(resultSet);
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

    public Book saveBook(Book book) {
        ResultSet resultSet = null;

        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement("INSERT INTO book (title, publisher, isbn) VALUES (?, ?, ?)")) {
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getPublisher());
            statement.setString(3, book.getIsbn());

            statement.execute();

            Statement lastIndex = connection.createStatement();

            // LAST_INSERT_ID is specific to MySQL
            resultSet = lastIndex.executeQuery("SELECT LAST_INSERT_ID()");

            if (resultSet.next()) {
                Long savedId = resultSet.getLong(1);
                return getById(savedId);
            }

            lastIndex.close();
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

    public Book updateBook(Book book) {
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        "UPDATE book set title = ?, publisher = ?, isbn = ? where id = ?")) {
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getPublisher());
            statement.setString(3, book.getIsbn());
            statement.setLong(4, book.getId());

            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return getById(book.getId());
    }

    public void deleteBook(Book book) {
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement("DELETE from book where id = ?")) {
            statement.setLong(1, book.getId());

            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Book constructBook(ResultSet resultSet) throws SQLException {
        var book = new Book();
        book.setId(resultSet.getLong("id"));
        book.setTitle(resultSet.getString("title"));
        book.setPublisher(resultSet.getString("publisher"));
        book.setIsbn(resultSet.getString("isbn"));

        return book;
    }
}
