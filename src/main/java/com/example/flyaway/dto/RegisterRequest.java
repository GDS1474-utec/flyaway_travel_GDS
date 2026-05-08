package com.example.flyaway.dto;

import jakarta.validation.constraints.*;

public record RegisterRequest(
        @NotBlank @Pattern(regexp = ".*[A-Z].*", message = "name must contain at least one uppercase letter") String name,
        @NotBlank @Pattern(regexp = ".*[A-Z].*", message = "lastname must contain at least one uppercase letter") String lastname,
        @NotBlank @Email String email,
        @NotBlank @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).{8,}$", message = "password must have 8 chars, 1 letter and 1 number") String password
) {}
