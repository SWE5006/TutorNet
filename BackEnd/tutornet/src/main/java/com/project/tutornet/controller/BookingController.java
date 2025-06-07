package com.project.tutornet.controller;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.tutornet.dto.CreateBookingRequest;
import com.project.tutornet.entity.Booking;
import com.project.tutornet.service.BookingService;
@RestController
@RequestMapping("/api/booking")
public class BookingController {

     @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody CreateBookingRequest request) {
        Booking booking = bookingService.createBooking(request);
        return ResponseEntity.ok(booking);
       
    }

    @GetMapping("/{studentid}")
    public ResponseEntity<List<Booking>> getBookingsByStudentId(@PathVariable("studentid") UUID studentId) {
        List<Booking> bookings = bookingService.getBookingsByStudentId(studentId);
        return ResponseEntity.ok(bookings);
    }
    
}
