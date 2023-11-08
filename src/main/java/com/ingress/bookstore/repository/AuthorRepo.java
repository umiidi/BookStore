package com.ingress.bookstore.repository;

import com.ingress.bookstore.models.domain.Author;
import com.ingress.bookstore.models.domain.Student;
import com.ingress.bookstore.repository.mapper.AuthorRowMapper;
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
public class AuthorRepo {

    private final NamedParameterJdbcTemplate authorJdbcTemplate;

    public int save(Author author) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String query = "insert into author(name, user_id, age) values(:name, :user_id, :age)";
        authorJdbcTemplate.update(
                query,
                new MapSqlParameterSource()
                        .addValue("name", author.getName())
                        .addValue("user_id", author.getUserId())
                        .addValue("age", author.getAge()),
                keyHolder);
        return keyHolder.getKey().intValue();
    }

    public Optional<Author> getByUserId(int id) {
        String query = "select * FROM author as a WHERE a.user_id = :id";
        try {
            return Optional.ofNullable(authorJdbcTemplate.queryForObject(
                    query,
                    new MapSqlParameterSource()
                            .addValue("id", id),
                    new AuthorRowMapper()));
        } catch (DataAccessException ignored) {
            return Optional.empty();
        }
    }

    public Optional<List<Student>> getSubscriber(int id){
        String query = "select s.*\n" +
                "from subscriptions as sb\n" +
                "LEFT JOIN student as s\n" +
                "ON sb.student_id = s.id\n" +
                "WHERE sb.author_id = :id";
        try {
            return Optional.of(authorJdbcTemplate.query(
                    query,
                    new MapSqlParameterSource()
                            .addValue("id", id),
                    new StudentRowMapper()));
        } catch (DataAccessException ignored) {
            return Optional.empty();
        }
    }

}
