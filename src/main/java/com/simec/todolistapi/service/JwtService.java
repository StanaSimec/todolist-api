package com.simec.todolistapi.service;

public interface JwtService {
    String generateForEmail(String email);

    void validateToken(String token);

    String extractEmail(String jwt);
}
