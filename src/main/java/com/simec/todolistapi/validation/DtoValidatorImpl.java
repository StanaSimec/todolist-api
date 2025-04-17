package com.simec.todolistapi.validation;

import com.simec.todolistapi.dao.UserDao;
import com.simec.todolistapi.dto.LoginDto;
import com.simec.todolistapi.dto.RegisterDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class DtoValidatorImpl implements DtoValidator {

    private final UserDao userDAO;

    @Autowired
    public DtoValidatorImpl(UserDao userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public void validateRegisterDto(RegisterDto user) {
        validateUsername(user.getUsername());
        validateEmailForRegistration(user.getEmail());
        validatePassword(user.getPassword());
    }

    public void validateLoginDto(LoginDto user) {
        validateEmailForLogin(user.getEmail());
        validatePassword(user.getPassword());
    }

    private void validateUsername(String username) {
        if (username == null) {
            throw new IllegalArgumentException("username is required");
        }

        if (username.isEmpty()) {
            throw new IllegalArgumentException("username is required");
        }

        if (username.length() > 20) {
            throw new IllegalArgumentException("maximal username length is 20");
        }
    }

    private void validateEmailForRegistration(String email) {
        if (email == null) {
            throw new IllegalArgumentException("email is required");
        }

        if (email.isEmpty()) {
            throw new IllegalArgumentException("email is required");
        }

        if (!userDAO.isEmailUnique(email)) {
            throw new IllegalArgumentException("email is already used");
        }

        Pattern pattern = Pattern.compile("^([a-zA-Z]{1,7})@apitodolist.com$");
        Matcher matcher = pattern.matcher(email);

        if (!matcher.matches()) {
            throw new IllegalArgumentException("email is not in format {name}@apitodolist.com");
        }
    }

    private void validateEmailForLogin(String email) {
        if (email == null) {
            throw new IllegalArgumentException("email is required");
        }

        if (email.isEmpty()) {
            throw new IllegalArgumentException("email is required");
        }

        if (email.length() > 50) {
            throw new IllegalArgumentException("email max length is 50 characters");
        }
    }

    private void validatePassword(String password) {
        if (password == null) {
            throw new IllegalArgumentException("email is required");
        }

        if (password.isEmpty()) {
            throw new IllegalArgumentException("username is required");
        }

        if (password.length() < 5) {
            throw new IllegalArgumentException("manimal paswword length is 5");
        }
    }
}
