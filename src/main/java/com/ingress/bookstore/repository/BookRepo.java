package com.ingress.bookstore.repository;

import com.ingress.bookstore.exception.types.NotFoundException;
import com.ingress.bookstore.models.domain.Book;
import com.ingress.bookstore.models.domain.Student;
import com.ingress.bookstore.repository.mapper.BookRowMapper;
import com.ingress.bookstore.repository.mapper.StudentRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookRepo {

    private final NamedParameterJdbcTemplate bookJdbcTemplate;

    public Optional<Book> getById(int id) {
        String query = "select b.*, a.`name` as author_name, a.age as author_age\n" +
                "from books as b\n" +
                "LEFT JOIN author as a\n" +
                "ON b.author_id = a.id\n" +
                "WHERE b.id = :id";
        try {
            return Optional.ofNullable(bookJdbcTemplate.queryForObject(
                    query,
                    new MapSqlParameterSource()
                            .addValue("id", id),
                    new BookRowMapper()));
        } catch (DataAccessException ignored) {
            return Optional.empty();
        }
    }

    public void read(int bookId, int studentId) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String query = "insert into booksread(book_id, student_id) values(:book_id, :student_id)";
        bookJdbcTemplate.update(
                query,
                new MapSqlParameterSource()
                        .addValue("book_id", bookId)
                        .addValue("student_id", studentId),
                keyHolder);
    }

    public Optional<List<Student>> getAllReaders(int id) {
        String query = "select s.*\n" +
                "from booksread as bs\n" +
                "LEFT JOIN student as s\n" +
                "ON bs.student_id = s.id\n" +
                "WHERE bs.book_id = :id";
        try {
            return Optional.of(bookJdbcTemplate.query(
                    query,
                    new MapSqlParameterSource()
                            .addValue("id", id),
                    new StudentRowMapper()));
        } catch (DataAccessException ignored) {
            return Optional.empty();
        }
    }

    public int save(Book u) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String query = "insert into books(name, author_id) values(:name, :author_id)";
        bookJdbcTemplate.update(
                query,
                new MapSqlParameterSource()
                        .addValue("name", u.getName())
                        .addValue("author_id", u.getAuthor().getId()),
                keyHolder);
        return keyHolder.getKey().intValue();

    }

    public void delete(int id) {
        String query = "delete from books where id = :id";
        try {
            bookJdbcTemplate.update(
                    query,
                    new MapSqlParameterSource()
                            .addValue("id", id));
        } catch (DataAccessException ex) {
            throw new NotFoundException("book not found");
        }
    }

    public boolean isReadByStudent(int bookId, int studentId) {
        String query = "select count(*) as 'count' from booksread where student_id = :student_id AND book_id = :book_id";
        int count = bookJdbcTemplate.queryForObject(
                query,
                new MapSqlParameterSource()
                        .addValue("student_id", studentId)
                        .addValue("book_id", bookId),
                (rs, rowNum) -> rs.getInt("count"));
        return count != 0;
    }

}
