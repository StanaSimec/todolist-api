package com.simec.todolistapi.controller;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.simec.todolistapi.dto.ErrorDto;
import com.simec.todolistapi.exception.TodoNotFoundException;
import com.simec.todolistapi.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TodoNotFoundException.class)
    public ResponseEntity<ErrorDto> handleTodoNotFound(Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto(e.getMessage()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDto> handleUserNotFound(Exception e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorDto(e.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDto> handleValidationException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDto(e.getMessage()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorDto> handleBadCredentials(Exception e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorDto(e.getMessage()));
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<ErrorDto> handleExpiredJwt(Exception e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorDto("Expired token"));
    }

    @ExceptionHandler(JWTVerificationException.class)
    public ResponseEntity<ErrorDto> handleInvalidJwt(Exception e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorDto("Invalid token"));
    }
}
