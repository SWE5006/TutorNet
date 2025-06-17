package com.project.tutornet.service;



import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.tutornet.dto.CreateBookingRequest;
import com.project.tutornet.entity.Booking;
import com.project.tutornet.entity.Student;
import com.project.tutornet.entity.TimeSlot;
import com.project.tutornet.repository.BookingRepository;
import com.project.tutornet.repository.StudentRepository;
import com.project.tutornet.repository.TimeSlotRepository;
@Service
public class BookingService {

     @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private StudentRepository studentRepository;

     @Autowired
    private TimeSlotRepository timeSlotRepository;
// Create a new booking
    public Booking createBooking(CreateBookingRequest request) {
        Student student = studentRepository.findById(request.getStudentId())
            .orElseThrow(() -> new RuntimeException("Student not found"));

        TimeSlot timeSlot = timeSlotRepository.findById(request.getSlotId())
            .orElseThrow(() -> new RuntimeException("Time slot not found"));

        Booking booking = new Booking();
        booking.setBookingStatus("ACTIVE");
        booking.setBookingDate(new Date());
        booking.setSubjectName(request.getSubjectName());
        booking.setStudent(student);
        booking.setSlot(timeSlot);

        return bookingRepository.save(booking);
    }
    
      // Get all bookings by student ID
    public List<Booking> getBookingsByStudentId(UUID studentId) {
        return bookingRepository.findByStudent_Id(studentId);
    }

   



    
}
