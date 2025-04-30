package com.simec.todolistapi.service;

import com.simec.todolistapi.AuthenticationFacade;
import com.simec.todolistapi.dao.TodoDao;
import com.simec.todolistapi.dao.UserDao;
import com.simec.todolistapi.dto.TodoRequestDto;
import com.simec.todolistapi.dto.TodoResponseDto;
import com.simec.todolistapi.entity.Todo;
import com.simec.todolistapi.entity.User;
import com.simec.todolistapi.exception.TodoNotFoundException;
import com.simec.todolistapi.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TodoServiceImpl implements TodoService {
    private final TodoDao todoDao;
    private final AuthenticationFacade authentication;
    private final UserDao userDao;

    @Autowired
    public TodoServiceImpl(TodoDao todoDao, AuthenticationFacade authentication, UserDao userDao) {
        this.todoDao = todoDao;
        this.authentication = authentication;
        this.userDao = userDao;
    }

    @Override
    public List<TodoResponseDto> findAllWithPaging(Integer page, Integer limit) {
        int todosPerPage = 10;
        int offset = todosPerPage * page;
        return todoDao.findAllWithPaging(offset, limit).stream()
                .map(t -> new TodoResponseDto(t.getId(), t.getTitle(), t.getDescription()))
                .toList();
    }

    @Override
    public TodoResponseDto create(TodoRequestDto dto) {
        User user = getPrincipalUser();
        Todo todo = new Todo.Builder()
                .withTitle(dto.title())
                .withDescription(dto.description())
                .withUserId(user.getId())
                .build();
        Todo created = todoDao.create(todo);
        return new TodoResponseDto(created.getId(), created.getTitle(), created.getDescription());
    }

    @Override
    public TodoResponseDto find(long id) {
        Todo todo = getTodoByTodoIdAndPersonId(id, getPrincipalUser().getId());
        return new TodoResponseDto(todo.getId(), todo.getTitle(), todo.getDescription());
    }

    @Override
    public TodoResponseDto update(long id, TodoRequestDto dto) {
        User user = getPrincipalUser();
        Todo todo = getTodoByTodoIdAndPersonId(id, user.getId());
        Todo updated = new Todo.Builder()
                .withId(todo.getId())
                .withTitle(dto.title())
                .withDescription(dto.description())
                .withUserId(user.getId())
                .build();
        todoDao.update(updated);
        Todo saved = getTodoByTodoIdAndPersonId(id, user.getId());
        return new TodoResponseDto(saved.getId(), saved.getTitle(), saved.getDescription());
    }

    @Override
    public void delete(long id) {
        Todo todo = getTodoByTodoIdAndPersonId(id, getPrincipalUser().getId());
        todoDao.deleteById(todo.getId());
    }

    private User getPrincipalUser() {
        String email = ((UserDetails) authentication.getAuthentication().getPrincipal()).getUsername();
        return userDao.findByEmail(email).orElseThrow(() -> new UserNotFoundException("Invalid user."));
    }

    private Todo getTodoByTodoIdAndPersonId(long todoId, long userId) {
        return todoDao.findByTodoIdAndPersonId(todoId, userId)
                .orElseThrow(() -> new TodoNotFoundException("Todo was not found"));
    }
}
