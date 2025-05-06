package com.simec.todolistapi.service;

import com.simec.todolistapi.dto.LoginRequestDto;
import com.simec.todolistapi.dto.RegisterRequestDto;
import com.simec.todolistapi.entity.User;

public interface UserService {
    User register(RegisterRequestDto registerDto);

    User login(LoginRequestDto loginDto);
}
