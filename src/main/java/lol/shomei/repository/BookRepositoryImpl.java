package lol.shomei.repository;

import lol.shomei.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
@SuppressWarnings("unused")
public class BookRepositoryImpl implements BookRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public long addBook(String title) {
        String sql = "insert into Book (title) values (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, title);
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();

        if (key == null) {
            throw new RuntimeException("add failed");
        }

        return key.longValue();
    }

    public long updateBook(long id, String title) {
        String sql = "update Book set title=? where id=?";

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, title);
            ps.setLong(2, id);
            return ps;
        });

        return id;
    }

    public Book getBookById(long id) {
        List<Book> books = jdbcTemplate.query("select id, title from Book where id=?",
                new Object[]{ id },
                (resultSet, i) -> toBook(resultSet));

        if (books.size() != 1) {
            throw new RuntimeException("book not found");
        }

        return books.get(0);
    }

    public List<Book> getAllBooks() {
        return jdbcTemplate.query("select id, title from Book",
                (resultSet, i) -> toBook(resultSet));
    }

    private Book toBook(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.id = resultSet.getLong("id");
        book.title = resultSet.getString("title");
        return book;
    }
}
