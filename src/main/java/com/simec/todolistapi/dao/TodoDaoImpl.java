package com.simec.todolistapi.dao;

import com.simec.todolistapi.entity.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.Objects;

@Component
public class TodoDaoImpl implements TodoDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TodoDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Todo create(Todo todo) {
        String sql = "INSERT INTO todo (title, description, person_id) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, todo.getTitle());
            preparedStatement.setString(2, todo.getDescription());
            preparedStatement.setLong(3, todo.getUserId());
            return preparedStatement;
        }, keyHolder);
        Objects.requireNonNull(keyHolder.getKeys());
        return new Todo.Builder()
                .withId((Integer) keyHolder.getKeys().get("id"))
                .withTitle((String) keyHolder.getKeys().get("title"))
                .withDescription((String) keyHolder.getKeys().get("description"))
                .withUserId((Integer) keyHolder.getKeys().get("person_id"))
                .build();
    }
}
