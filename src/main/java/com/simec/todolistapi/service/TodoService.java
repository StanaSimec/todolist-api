package com.simec.todolistapi.service;

import com.simec.todolistapi.dto.TodoRequestDto;
import com.simec.todolistapi.dto.TodoResponseDto;

public interface TodoService {
    TodoResponseDto create(TodoRequestDto dto);

    TodoResponseDto find(long id);

    TodoResponseDto update(long id, TodoRequestDto dto);

    void delete(long id);
}
