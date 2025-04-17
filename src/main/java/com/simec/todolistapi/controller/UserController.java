package com.simec.todolistapi.controller;

import com.simec.todolistapi.dto.LoginDto;
import com.simec.todolistapi.dto.RegisterDto;
import com.simec.todolistapi.dto.TokenDto;
import com.simec.todolistapi.entity.User;
import com.simec.todolistapi.service.JwtService;
import com.simec.todolistapi.service.UserService;
import com.simec.todolistapi.validation.DtoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;
    private final DtoValidator dtoValidator;
    private final JwtService jwtService;

    @Autowired
    public UserController(UserService userService, DtoValidator dtoValidator, JwtService jwtService) {
        this.userService = userService;
        this.dtoValidator = dtoValidator;
        this.jwtService = jwtService;
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TokenDto> register(@RequestBody RegisterDto registerDto) {
        dtoValidator.validateRegisterDto(registerDto);
        User user = userService.register(registerDto);
        String token = jwtService.generateForEmail(user.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(new TokenDto(token));
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TokenDto> login(@RequestBody LoginDto loginDto) {
        dtoValidator.validateLoginDto(loginDto);
        User user = userService.login(loginDto);
        String token = jwtService.generateForEmail(user.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(new TokenDto(token));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> validationException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
