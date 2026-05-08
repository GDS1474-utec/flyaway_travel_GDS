package com.example.flyaway.service;

import com.example.flyaway.dto.*;
import com.example.flyaway.entity.User;
import com.example.flyaway.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) throw new RuntimeException("Email already exists");
        User user = User.builder()
                .name(request.name()).lastname(request.lastname()).email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .build();
        return new RegisterResponse(userRepository.save(user).getId());
    }
}
