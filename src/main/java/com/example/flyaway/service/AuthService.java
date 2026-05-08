package com.example.flyaway.service;

import com.example.flyaway.dto.*;
import com.example.flyaway.repository.UserRepository;
import com.example.flyaway.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public LoginResponse login(LoginRequest request) {
        var user = userRepository.findByEmail(request.email()).orElseThrow(() -> new RuntimeException("Unknown email"));
        if (!passwordEncoder.matches(request.password(), user.getPassword())) throw new RuntimeException("Incorrect password");
        return new LoginResponse(jwtService.generateToken(user.getEmail()));
    }
}
