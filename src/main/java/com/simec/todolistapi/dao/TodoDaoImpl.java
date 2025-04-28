package com.simec.todolistapi.dao;

import com.simec.todolistapi.entity.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;

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

    @Override
    public Optional<Todo> findByTodoIdAndPersonId(long todoId, long personId) {
        String sql = "SELECT id, title, description, person_id FROM todo WHERE id = ? AND person_id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new TodoRowMapper(), todoId, personId));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void update(Todo todo) {
        String sql = "UPDATE todo SET title = ?, description = ? WHERE id = ?";
        jdbcTemplate.update(sql, todo.getTitle(), todo.getDescription(), todo.getId());
    }

    @Override
    public void deleteById(long id) {
        String sql = "DELETE FROM todo WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private static class TodoRowMapper implements RowMapper<Todo> {
        @Override
        public Todo mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Todo.Builder()
                    .withId(rs.getLong("id"))
                    .withTitle(rs.getString("title"))
                    .withDescription(rs.getString("description"))
                    .withUserId(rs.getLong("person_id"))
                    .build();
        }
    }
}
