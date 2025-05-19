package com.project.tutornet.repository;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.tutornet.model.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID>  {
    boolean existsByBookingId(UUID bookingId);
}
