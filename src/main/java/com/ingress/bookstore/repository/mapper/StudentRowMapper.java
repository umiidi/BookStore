package com.ingress.bookstore.repository.mapper;

import com.ingress.bookstore.models.domain.Student;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentRowMapper implements RowMapper<Student> {

    @Override
    public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Student.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .userId(rs.getInt("user_id"))
                .age(rs.getInt("age"))
                .build();
    }

}
