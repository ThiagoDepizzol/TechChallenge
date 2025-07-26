package com.tech.challenge.TechChallenge.util;

import jakarta.validation.constraints.NotNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class HashService {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


    public static String hash(@NotNull final String password) {
        return encoder.encode(password);
    }
}
