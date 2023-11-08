package com.ingress.bookstore.repository;

import com.ingress.bookstore.models.domain.User;
import com.ingress.bookstore.repository.mapper.UserRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepo {

    private final NamedParameterJdbcTemplate userJdbcTemplate;

    public Optional<User> getByUsername(String username) {
        String query = "select * from user where username = :username";
        return Optional.ofNullable(userJdbcTemplate.queryForObject(
                query,
                new MapSqlParameterSource()
                        .addValue("username", username),
                new UserRowMapper()));
    }

    public int save(User u) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        System.out.println(u);
        String query = "insert into user(username, password, authority) values(:username, :password, :authority)";
        try {
            userJdbcTemplate.update(
                    query,
                    new MapSqlParameterSource()
                            .addValue("username", u.getUsername())
                            .addValue("password", u.getPassword())
                            .addValue("authority", u.getAuthority().name()),
                    keyHolder);
            return Objects.requireNonNull(keyHolder.getKey()).intValue();
        } catch (Exception ex) {
            throw new RuntimeException("user not added!");
        }
    }

    public boolean existsByUsername(String username) {
        String query = "select count(*) as count from user where username = :username ";
        int count = userJdbcTemplate.queryForObject(
                query,
                new MapSqlParameterSource()
                        .addValue("username", username),
                (rs, rowNum) -> rs.getInt("count")
        );
        return count != 0;
    }

}
