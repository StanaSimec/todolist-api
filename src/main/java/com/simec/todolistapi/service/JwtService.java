package com.simec.todolistapi.service;

import java.util.Optional;

public interface JwtService {
    String generateForEmail(String email);

    boolean isValid(String token);

    Optional<String> extractEmail(String jwt);
}
