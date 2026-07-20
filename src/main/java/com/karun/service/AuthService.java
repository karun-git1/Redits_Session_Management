package com.karun.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.karun.dto.LoginRequest;
import com.karun.dto.LoginResponse;
import com.karun.dto.RegisterRequest;
import com.karun.entity.User;
import com.karun.repository.UserRepository;
import com.karun.util.TokenGenerator;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenGenerator tokenGenerator;

    @Autowired
    private RedisSessionService redisSessionService;

    /**
     * Register User
     */
    public String register(RegisterRequest request) {

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return "Username already exists";
        }

        User user = new User();

        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setEmail(request.getEmail());

        userRepository.save(user);

        return "User Registered Successfully";
    }

    /**
     * Login
     */
    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid Username"));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Invalid Password");
        }

        String token = tokenGenerator.generateToken();

        redisSessionService.createSession(token, user.getId());

        return new LoginResponse(
                "Login Successful",
                token);
    }

    /**
     * Logout
     */
    public String logout(String token) {

        redisSessionService.deleteSession(token);

        return "Logout Successful";
    }

    /**
     * Validate Session
     */
    public boolean validateSession(String token) {

        return redisSessionService.isSessionValid(token);
    }

}