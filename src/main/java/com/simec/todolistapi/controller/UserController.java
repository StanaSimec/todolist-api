package com.simec.todolistapi.controller;

import com.simec.todolistapi.dto.LoginDto;
import com.simec.todolistapi.dto.RegisterDto;
import com.simec.todolistapi.dto.TokenDto;
import com.simec.todolistapi.entity.User;
import com.simec.todolistapi.service.JwtService;
import com.simec.todolistapi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

    @Autowired
    public UserController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TokenDto> register(@RequestBody @Valid RegisterDto registerDto) {
        User user = userService.register(registerDto);
        String token = jwtService.generateForEmail(user.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(new TokenDto(token));
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TokenDto> login(@RequestBody @Valid LoginDto loginDto) {
        User user = userService.login(loginDto);
        String token = jwtService.generateForEmail(user.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(new TokenDto(token));
    }
}
