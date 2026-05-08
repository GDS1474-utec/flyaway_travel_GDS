package com.example.flyaway.repository;

import com.example.flyaway.entity.Booking;
import com.example.flyaway.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUser(User user);
}
