package com.simec.todolistapi.controller;

import com.simec.todolistapi.dto.ErrorDto;
import com.simec.todolistapi.dto.TodoRequestDto;
import com.simec.todolistapi.dto.TodoResponseDto;
import com.simec.todolistapi.exception.TodoNotFoundException;
import com.simec.todolistapi.exception.UserNotFoundException;
import com.simec.todolistapi.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/todos")
public class TodoController {

    private final TodoService todoService;

    @Autowired
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping
    public ResponseEntity<TodoResponseDto> create(@RequestBody TodoRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(todoService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoResponseDto> update(@PathVariable Integer id, @RequestBody TodoRequestDto dto) {
        return ResponseEntity.status(HttpStatus.OK).body(todoService.update(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoResponseDto> find(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(todoService.find(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TodoResponseDto> delete(@PathVariable Integer id) {
        todoService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ExceptionHandler(TodoNotFoundException.class)
    public ResponseEntity<ErrorDto> handleTodoNotFound(Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto(e.getMessage()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDto> handleUserNotFound(Exception e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorDto(e.getMessage()));
    }
}
