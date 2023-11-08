package com.ingress.bookstore.repository.mapper;

import com.ingress.bookstore.models.domain.Author;
import com.ingress.bookstore.models.domain.Book;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookRowMapper implements RowMapper<Book> {

    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Book.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .author(Author.builder()
                        .id(rs.getInt("author_id"))
                        .name(rs.getString("author_name"))
                        .age(rs.getInt("author_age"))
                        .build())
                .build();
    }

}
