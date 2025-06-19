package com.project.tutornet.service;



import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.tutornet.dto.BookingResponseDto;
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
    public void createBooking(CreateBookingRequest request) {
        System.out.println("Creating booking for student Email: " + request.getStudentEmail());
        Student student = studentRepository.findByUserInfoEmailAddress(request.getStudentEmail())
            .orElseThrow(() -> new RuntimeException("Student not found"));

        for (String slotId : request.getSlotId()) {
            TimeSlot timeSlot = timeSlotRepository.findById(UUID.fromString(slotId))
                .orElseThrow(() -> new RuntimeException("Time slot not found"));
           
        Booking booking = new Booking();
        booking.setBookingStatus("ACTIVE");
        booking.setBookingDate(new Date());
        booking.setSubjectName(request.getSubjectName());
        booking.setStudentName(student.getUsername());
        booking.setNumberOfBooking(request.getNumberOfBooking());
        booking.setStudentId(student.getId().toString());
        booking.setSlot(slotId != null ? timeSlot : null);
         bookingRepository.save(booking);
        }

        
    }
    
    //   // Get all bookings by student ID
    // public List<Booking> getBookingsByStudentId(UUID studentId) {
    //     return bookingRepository.findByStudent_Id(studentId);
    // }

   public List<BookingResponseDto> getBookingsByStudentId(String studentId) {
        List<Booking> bookings = bookingRepository.findByStudentId(studentId);
        return bookings.stream()
                       .map(booking -> new BookingResponseDto(
                           booking.getBookingId(),
                           booking.getBookingDate(),
                           booking.getBookingStatus(),
                           booking.getSubjectName(),
                           booking.getNumberOfBooking(),
                           booking.getStudentName(),
                           booking.getSlot().getDayOfWeek() + "" + " " + booking.getSlot().getStartTime() + " - " + booking.getSlot().getEndTime(),
                           booking.getSlot().getTutor().getUsername()
                            ))
                       .collect(Collectors.toList());
    }

    
public List<BookingResponseDto> getBookingsByEmail(String emailAddress) {

        Student student = studentRepository.findByUserInfoEmailAddress(emailAddress)
            .orElseThrow(() -> new RuntimeException("Student not found"));
        List<Booking> bookings = bookingRepository.findByStudentId(student.getId().toString());
        return bookings.stream()
                       .map(booking -> new BookingResponseDto(
                           booking.getBookingId(),
                           booking.getBookingDate(),
                           booking.getBookingStatus(),
                           booking.getSubjectName(),
                           booking.getNumberOfBooking(),
                           booking.getStudentName(),
                           booking.getSlot().getDayOfWeek() + booking.getSlot().getStartTime() + " - " + booking.getSlot().getEndTime(),
                           booking.getSlot().getTutor().getUsername()
                            ))
                       .collect(Collectors.toList());
    }


    
}
