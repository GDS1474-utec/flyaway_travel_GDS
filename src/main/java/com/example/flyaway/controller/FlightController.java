package com.example.flyaway.controller;

import com.example.flyaway.dto.*;
import com.example.flyaway.entity.Flight;
import com.example.flyaway.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/flights")
@RequiredArgsConstructor
public class FlightController {
    private final FlightService flightService;
    private final BookingService bookingService;

    @PostMapping("/create")
    public Flight create(@Valid @RequestBody FlightRequest request) {
        return flightService.create(request);
    }

    @GetMapping("/search")
    public List<Flight> search(
            @RequestParam(required = false) String flightNumber,
            @RequestParam(required = false) String airline,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        return flightService.search(flightNumber, airline, from, to);
    }

    @GetMapping("/{id}")
    public Flight getFlight(@PathVariable Long id) {
        return flightService.getById(id);
    }

    @PostMapping("/book")
    public BookingResponse book(@Valid @RequestBody BookingRequest request) {
        return bookingService.book(request);
    }
}
