package com.simec.todolistapi.dto;

import com.simec.todolistapi.validator.UniqueEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record RegisterRequestDto(
    @NotBlank(message = "username is required")
    @Length(min = 3, max = 50, message = "username length should be between 3 to 50 characters")
    String username,
    @NotBlank(message = "email is required")
    @Email(message = "email is invalid")
    @UniqueEmail
    String email,
    @NotBlank(message = "password is required")
    @Length(min = 5, max = 50, message = "password length should be from 5 to 50 characters")
    String password) {}
