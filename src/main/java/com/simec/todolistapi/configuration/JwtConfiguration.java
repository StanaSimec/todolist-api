package com.simec.todolistapi.configuration;

import com.auth0.jwt.algorithms.Algorithm;
import com.simec.todolistapi.properties.JwtProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfiguration {
    private final JwtProperties jwtProperties;

    @Autowired
    public JwtConfiguration(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    @Bean
    public Algorithm algorithm() {
        return Algorithm.HMAC256(jwtProperties.getSecret());
    }
}
