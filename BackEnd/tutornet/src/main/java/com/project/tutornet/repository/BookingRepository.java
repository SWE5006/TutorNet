package com.project.tutornet.repository;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.tutornet.entity.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID>  {
   boolean existsByBookingId(UUID bookingId);
   List<Booking> findByStudentId(String studentId);
   
}
