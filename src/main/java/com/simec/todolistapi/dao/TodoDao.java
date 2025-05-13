package com.simec.todolistapi.dao;

import com.simec.todolistapi.entity.Todo;

import java.util.List;
import java.util.Optional;

public interface TodoDao {
    List<Todo> findAll(Integer offset, Integer limit, long personId);

    Todo create(Todo todo);

    Optional<Todo> findById(long todoId, long personId);

    void update(Todo todo);

    void deleteById(long id);

    Integer getTotalCount();
}
