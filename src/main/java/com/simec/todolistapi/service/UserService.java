package com.simec.todolistapi.service;

import com.simec.todolistapi.dto.LoginDto;
import com.simec.todolistapi.dto.RegisterDto;
import com.simec.todolistapi.entity.User;

public interface UserService {
    User register(RegisterDto registerDto);

    User login(LoginDto loginDto);
}
