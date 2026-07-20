package com.karun.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.karun.dto.LoginRequest;
import com.karun.dto.LoginResponse;
import com.karun.dto.RegisterRequest;
import com.karun.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * Register a new user
     */
    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    /**
     * Login
     */
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    /**
     * Logout
     */
    @PostMapping("/logout")
    public String logout(@RequestParam String token) {
        return authService.logout(token);
    }

    /**
     * Validate Session
     */
    @GetMapping("/validate")
    public String validate(@RequestParam String token) {

        boolean valid = authService.validateSession(token);

        if (valid) {
            return "Session is Valid";
        }

        return "Session Expired";
    }
}