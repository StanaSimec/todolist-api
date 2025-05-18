package com.simec.todolistapi.controller;

import com.simec.todolistapi.dto.LoginRequestDto;
import com.simec.todolistapi.dto.RegisterRequestDto;
import com.simec.todolistapi.dto.TokenResponseDto;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
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
    public ResponseEntity<TokenResponseDto> register(@RequestBody @Valid RegisterRequestDto registerDto) {
        User user = userService.register(registerDto);
        String token = jwtService.generateForEmail(user.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(new TokenResponseDto(token));
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TokenResponseDto> login(@RequestBody @Valid LoginRequestDto loginDto) {
        User user = userService.login(loginDto);
        String token = jwtService.generateForEmail(user.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(new TokenResponseDto(token));
    }
}
