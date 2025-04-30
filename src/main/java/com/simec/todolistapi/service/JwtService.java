package com.simec.todolistapi.service;

import java.util.Optional;

public interface JwtService {
    String generateForEmail(String email);

    void validateToken(String token);

    String extractEmail(String jwt);
}
