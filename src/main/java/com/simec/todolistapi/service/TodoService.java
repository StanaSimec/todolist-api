package com.simec.todolistapi.service;

import com.simec.todolistapi.dto.TodoRequestDto;
import com.simec.todolistapi.dto.TodoResponseDto;

import java.util.List;

public interface TodoService {
    List<TodoResponseDto> findAllWithPaging(Integer page, Integer limit);

    TodoResponseDto create(TodoRequestDto dto);

    TodoResponseDto find(long id);

    TodoResponseDto update(long id, TodoRequestDto dto);

    void delete(long id);
}
