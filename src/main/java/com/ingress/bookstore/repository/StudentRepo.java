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
public class StudentRepo {

    private final NamedParameterJdbcTemplate studentJdbcTemplate;

    public int save(Student student) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String query = "insert into student(name, user_id, age) values(:name, :user_id, :age)";
        studentJdbcTemplate.update(
                query,
                new MapSqlParameterSource()
                        .addValue("name", student.getName())
                        .addValue("user_id", student.getUserId())
                        .addValue("age", student.getAge()),
                keyHolder);
        return keyHolder.getKey().intValue();
    }

    public Optional<Student> getByUserId(int id) {
        String query = "select * FROM student as s WHERE s.user_id = :id";
        try {
            return Optional.ofNullable(studentJdbcTemplate.queryForObject(
                    query,
                    new MapSqlParameterSource()
                            .addValue("id", id),
                    new StudentRowMapper()));
        } catch (DataAccessException ignored) {
            return Optional.empty();
        }
    }

    public Optional<List<Book>> getReadBooks(int studentId) {
        String query = "select b.*, a.name as author_name, a.age as author_age\n" +
                "FROM booksread as bs\n" +
                "LEFT JOIN books as b\n" +
                "ON bs.book_id = b.id\n" +
                "LEFT JOIN author as a\n" +
                "ON b.author_id = a.id\n" +
                "WHERE bs.student_id = :id";
        try {
            return Optional.of(studentJdbcTemplate.query(
                    query,
                    new MapSqlParameterSource()
                            .addValue("id", studentId),
                    new BookRowMapper()));
        } catch (DataAccessException ignored) {
            return Optional.empty();
        }
    }


    public void subscribe(int studentId, int authorId) {
        String query = "insert into subscriptions(student_id, author_id) values(:student_id, :author_id)";
        try {
            studentJdbcTemplate.update(
                    query,
                    new MapSqlParameterSource()
                            .addValue("student_id", studentId)
                            .addValue("author_id", authorId));
        } catch (DataAccessException ignored) {
            throw new NotFoundException("author not found");
        }
    }

    public boolean isSubscribe(int studentId, int authorId) {
        String query = "select count(*) as 'count' from subscriptions where student_id = :student_id AND author_id = :author_id";
        int count = studentJdbcTemplate.queryForObject(
                query,
                new MapSqlParameterSource()
                        .addValue("student_id", studentId)
                        .addValue("author_id", authorId),
                (rs, rowNum) -> rs.getInt("count"));
        return count != 0;
    }

}
