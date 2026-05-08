package com.example.flyaway.service;

import com.example.flyaway.dto.FlightRequest;
import com.example.flyaway.entity.Flight;
import com.example.flyaway.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightService {
    private final FlightRepository flightRepository;

    public Flight create(FlightRequest request) {
        if (!request.departureTime().isBefore(request.arrivalTime())) throw new RuntimeException("Departure time must be before arrival time");
        if (flightRepository.existsByFlightNumber(request.flightNumber())) throw new RuntimeException("Flight number already exists");
        return flightRepository.save(Flight.builder()
                .flightNumber(request.flightNumber()).airline(request.airline()).origin(request.origin()).destination(request.destination())
                .departureTime(request.departureTime()).arrivalTime(request.arrivalTime()).availableSeats(request.availableSeats())
                .build());
    }

    public List<Flight> search(String flightNumber, String airline, LocalDateTime from, LocalDateTime to) {
        if (from != null && to != null) return flightRepository.findByDepartureTimeBetween(from, to);
        String fn = flightNumber == null ? "" : flightNumber;
        String al = airline == null ? "" : airline;
        return flightRepository.findByFlightNumberContainingIgnoreCaseOrAirlineContainingIgnoreCase(fn, al);
    }

    public Flight getById(Long id) {
        return flightRepository.findById(id).orElseThrow(() -> new RuntimeException("Flight not found"));
    }
}
