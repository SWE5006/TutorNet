package com.project.tutornet.repository;
import java.util.UUID;

import com.project.tutornet.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID>  {
    boolean existsByBookingId(UUID bookingId);
}
