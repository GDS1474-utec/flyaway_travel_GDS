package com.example.flyaway.service;

import com.example.flyaway.dto.*;
import com.example.flyaway.entity.*;
import com.example.flyaway.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.FileWriter;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final FlightRepository flightRepository;
    private final UserRepository userRepository;

    @Transactional
    public BookingResponse book(BookingRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        Flight flight = flightRepository.findById(request.flightId()).orElseThrow(() -> new RuntimeException("Flight not found"));

        if (flight.getAvailableSeats() <= 0) throw new RuntimeException("No seats available");
        if (!flight.getDepartureTime().isAfter(LocalDateTime.now())) throw new RuntimeException("Cannot book past or in-transit flights");

        for (Booking b : bookingRepository.findByUser(user)) {
            var aStart = flight.getDepartureTime();
            var aEnd = flight.getArrivalTime();
            var bStart = b.getFlight().getDepartureTime();
            var bEnd = b.getFlight().getArrivalTime();
            if (aStart.isBefore(bEnd) && aEnd.isAfter(bStart)) throw new RuntimeException("Schedule conflict with another booking");
        }

        flight.setAvailableSeats(flight.getAvailableSeats() - 1);
        flightRepository.save(flight);

        Booking booking = bookingRepository.save(Booking.builder()
                .user(user).flight(flight).customerName(user.getName() + " " + user.getLastname()).bookingDate(LocalDateTime.now())
                .build());
        writeEmailFile(booking);
        return toResponse(booking);
    }

    public BookingResponse get(Long id) {
        return toResponse(bookingRepository.findById(id).orElseThrow(() -> new RuntimeException("Booking not found")));
    }

    private BookingResponse toResponse(Booking b) {
        Flight f = b.getFlight();
        return new BookingResponse(b.getId(), b.getCustomerName(), f.getFlightNumber(), f.getAirline(), f.getDepartureTime(), f.getArrivalTime(), b.getBookingDate());
    }

    private void writeEmailFile(Booking b) {
        try (FileWriter fw = new FileWriter("flight_booking_email_" + b.getId() + ".txt")) {
            Flight f = b.getFlight();
            fw.write("Customer: " + b.getCustomerName() + "\n");
            fw.write("Flight Number: " + f.getFlightNumber() + "\n");
            fw.write("Departure Time: " + f.getDepartureTime() + "\n");
            fw.write("Arrival Time: " + f.getArrivalTime() + "\n");
        } catch (Exception ignored) { }
    }
}
