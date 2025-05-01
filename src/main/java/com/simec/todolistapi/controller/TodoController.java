package com.simec.todolistapi.controller;

import com.simec.todolistapi.dto.TodoPagedResponseDto;
import com.simec.todolistapi.dto.TodoRequestDto;
import com.simec.todolistapi.dto.TodoResponseDto;
import com.simec.todolistapi.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TodoPagedResponseDto> find(@RequestParam(name = "page", defaultValue = "1", required = false) Integer page,
                                                     @RequestParam(name = "limit", defaultValue = "10", required = false) Integer limit) {
        if (page <= 0 || page > 100 || limit <= 0 || limit > 100) {
            throw new IllegalArgumentException("Page and limit must be between 1 and 100");
        }
        return ResponseEntity.status(HttpStatus.OK).body(todoService.findAllWithPaging(page, limit));
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
}
