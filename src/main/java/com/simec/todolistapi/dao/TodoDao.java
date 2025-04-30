package com.simec.todolistapi.dao;

import com.simec.todolistapi.entity.Todo;

import java.util.List;
import java.util.Optional;

public interface TodoDao {
    List<Todo> findAllWithPaging(Integer offset, Integer limit);

    Todo create(Todo todo);

    Optional<Todo> findByTodoIdAndPersonId(long todoId, long personId);

    void update(Todo todo);

    void deleteById(long id);
}
