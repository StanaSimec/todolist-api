package com.simec.todolistapi.controller;

import com.simec.todolistapi.dao.TodoDao;
import com.simec.todolistapi.dao.UserDao;
import com.simec.todolistapi.dto.CreateTodoDto;
import com.simec.todolistapi.dto.TodoResponseDto;
import com.simec.todolistapi.entity.Todo;
import com.simec.todolistapi.entity.User;
import com.simec.todolistapi.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TodoController {

    private final TodoDao todoDao;
    private final UserDao userDao;

    @Autowired
    public TodoController(TodoDao todoDao, UserDao userDao) {
        this.todoDao = todoDao;
        this.userDao = userDao;
    }

    @PostMapping("/todos")
    public ResponseEntity<TodoResponseDto> create(@RequestBody CreateTodoDto dto) {
        String email = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userDao.findByEmail(email).orElseThrow(() -> new UserNotFoundException("user not found"));
        Todo todo = new Todo.Builder()
                .withTitle(dto.getTitle())
                .withDescription(dto.getDescription())
                .withUserId(user.getId())
                .build();
        Todo created = todoDao.create(todo);
        return ResponseEntity.status(HttpStatus.CREATED).body(new TodoResponseDto(created.getTitle(), created.getDescription()));
    }
}
