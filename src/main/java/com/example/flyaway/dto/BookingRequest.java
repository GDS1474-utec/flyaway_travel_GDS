package com.example.flyaway.dto;
import jakarta.validation.constraints.*;
public record BookingRequest(@NotNull Long flightId) {}
