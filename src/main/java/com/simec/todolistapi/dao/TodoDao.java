package com.simec.todolistapi.dao;

import com.simec.todolistapi.entity.Todo;

import java.util.Optional;

public interface TodoDao {
    Todo create(Todo todo);

    Optional<Todo> findByTodoIdAndPersonId(long todoId, long personId);

    void update(Todo todo);

    void deleteById(long id);
}
