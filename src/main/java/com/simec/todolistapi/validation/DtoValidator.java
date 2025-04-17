package com.simec.todolistapi.validation;

import com.simec.todolistapi.dto.LoginDto;
import com.simec.todolistapi.dto.RegisterDto;

public interface DtoValidator {
    void validateRegisterDto(RegisterDto user);

    void validateLoginDto(LoginDto user);
}
