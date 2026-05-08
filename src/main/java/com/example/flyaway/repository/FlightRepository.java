package com.example.flyaway.repository;

import com.example.flyaway.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    boolean existsByFlightNumber(String flightNumber);
    List<Flight> findByFlightNumberContainingIgnoreCaseOrAirlineContainingIgnoreCase(String flightNumber, String airline);
    List<Flight> findByDepartureTimeBetween(LocalDateTime from, LocalDateTime to);
}
