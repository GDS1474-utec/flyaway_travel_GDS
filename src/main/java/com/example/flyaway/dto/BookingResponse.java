package com.example.flyaway.dto;
import java.time.LocalDateTime;
public record BookingResponse(Long id, String customerName, String flightNumber, String airline, LocalDateTime departureTime, LocalDateTime arrivalTime, LocalDateTime bookingDate) {}
