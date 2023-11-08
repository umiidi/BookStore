package com.ingress.bookstore.repository.mapper;

import com.ingress.bookstore.models.domain.User;
import com.ingress.bookstore.models.enums.Authority;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return User.builder()
                .id(rs.getInt("id"))
                .username(rs.getString("username"))
                .password(rs.getString("password"))
                .authority(Authority.valueOf(rs.getString("authority")))
                .build();
    }
}

