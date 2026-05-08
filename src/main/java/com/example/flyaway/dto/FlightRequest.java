package com.example.flyaway.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public record FlightRequest(
        @NotBlank @Pattern(regexp = "^[A-Z0-9]{1,6}$", message = "flightNumber must be A-Z/0-9 and max 6 chars") String flightNumber,
        @NotBlank String airline,
        @NotBlank String origin,
        @NotBlank String destination,
        @NotNull LocalDateTime departureTime,
        @NotNull LocalDateTime arrivalTime,
        @NotNull @Positive Integer availableSeats
) {}
