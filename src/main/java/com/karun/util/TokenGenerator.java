package com.karun.util;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class TokenGenerator {

    public String generateToken() {
        return UUID.randomUUID().toString();
    }
}