package com.simec.todolistapi.dto;

import com.simec.todolistapi.validator.UniqueEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public class RegisterRequestDto {

    @NotBlank(message = "username is required")
    @Length(min = 3, max = 50, message = "username length should be between 3 to 50 characters")
    private String username;

    @NotBlank(message = "email is required")
    @Email(message = "email is invalid")
    @UniqueEmail
    private String email;

    @NotBlank(message = "password is required")
    @Length(min = 5, max = 50, message = "password length should be from 5 to 50 characters")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
