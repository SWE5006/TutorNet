package com.project.tutornet.repository;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.tutornet.model.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID>  {
    boolean existsByBookingId(UUID bookingId);
    List<Booking> findByStudent_UserId(UUID studentId);
    List<Booking> findByStudent_UserIdAndBookingStatus(UUID studentId, String status);
}
