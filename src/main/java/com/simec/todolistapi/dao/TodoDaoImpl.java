package com.simec.todolistapi.dao;

import com.simec.todolistapi.entity.Todo;
import com.simec.todolistapi.exception.DataSourceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
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
    public List<Todo> findAll(Integer offset, Integer limit, long personId) {
        String sql = "SELECT id, title, description, person_id FROM todo WHERE person_id = ? OFFSET ? LIMIT ?";
        try {
            return jdbcTemplate.query(sql, new TodoRowMapper(), personId, offset, limit);
        } catch (EmptyResultDataAccessException e) {
            return List.of();
        } catch (DataAccessException e) {
            throw new DataSourceException(e);
        }
    }

    @Override
    public Todo create(Todo todo) {
        String sql = "INSERT INTO todo (title, description, person_id) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
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
        } catch (Exception e) {
            throw new DataSourceException(e);
        }
    }

    @Override
    public Optional<Todo> findById(long todoId, long personId) {
        String sql = "SELECT id, title, description, person_id FROM todo WHERE id = ? AND person_id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new TodoRowMapper(), todoId, personId));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        } catch (DataAccessException e) {
            throw new DataSourceException(e);
        }
    }

    @Override
    public void update(Todo todo) {
        String sql = "UPDATE todo SET title = ?, description = ? WHERE id = ?";
        try {
            jdbcTemplate.update(sql, todo.getTitle(), todo.getDescription(), todo.getId());
        } catch (DataAccessException e) {
            throw new DataSourceException(e);
        }
    }

    @Override
    public void deleteById(long id) {
        String sql = "DELETE FROM todo WHERE id = ?";
        try {
            jdbcTemplate.update(sql, id);
        } catch (DataAccessException e) {
            throw new DataSourceException(e);
        }
    }

    @Override
    public Integer getTotalCount() {
        String sql = "SELECT COUNT(*) FROM todo";
        try {
            return jdbcTemplate.queryForObject(sql, Integer.class);
        } catch (DataAccessException e) {
            throw new DataSourceException(e);
        }
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
