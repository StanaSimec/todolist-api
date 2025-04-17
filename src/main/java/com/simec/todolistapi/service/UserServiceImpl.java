package com.simec.todolistapi.service;

import com.simec.todolistapi.dao.UserDao;
import com.simec.todolistapi.dto.LoginDto;
import com.simec.todolistapi.dto.RegisterDto;
import com.simec.todolistapi.entity.User;
import com.simec.todolistapi.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserDao userDAO;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserServiceImpl(UserDao userDAO, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.passwordEncoder = passwordEncoder;
        this.userDAO = userDAO;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public User register(RegisterDto registerRequestDTO) {
        User user = new User.Builder()
                .withUsername(registerRequestDTO.getUsername())
                .withEmail(registerRequestDTO.getEmail())
                .withPassword(passwordEncoder.encode(registerRequestDTO.getPassword()))
                .build();
        return userDAO.create(user);
    }

    @Override
    public User login(LoginDto loginDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(),
                loginDto.getPassword()
        ));
        return userDAO.findByEmail(loginDto.getEmail()).orElseThrow(() -> new UserNotFoundException("user not found"));
    }
}
