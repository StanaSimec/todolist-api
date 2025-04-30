package com.simec.todolistapi.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.simec.todolistapi.properties.JwtProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class JwtServiceImpl implements JwtService {

    private static final String EMAIL = "email";
    private final Algorithm algorithm;
    private final JwtProperties jwtProperties;

    @Autowired
    public JwtServiceImpl(Algorithm algorithm, JwtProperties jwtProperties) {
        this.algorithm = algorithm;
        this.jwtProperties = jwtProperties;
    }

    @Override
    public String generateForEmail(String email) {
        return JWT.create()
                .withIssuer(jwtProperties.getIssuer())
                .withClaim(EMAIL, email)
                .withExpiresAt(Instant.now().plus(jwtProperties.getDuration()))
                .sign(algorithm);
    }

    @Override
    public void validateToken(String token) {
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(jwtProperties.getIssuer())
                .build();
        verifier.verify(token);
    }

    @Override
    public String extractEmail(String token) {
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(jwtProperties.getIssuer())
                .build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim(EMAIL).asString();
    }
}
