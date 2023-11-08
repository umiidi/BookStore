package com.ingress.bookstore.repository.mapper;

import com.ingress.bookstore.models.domain.Author;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorRowMapper implements RowMapper<Author> {

    @Override
    public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Author.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .userId(rs.getInt("user_id"))
                .age(rs.getInt("age"))
                .build();
    }

}
