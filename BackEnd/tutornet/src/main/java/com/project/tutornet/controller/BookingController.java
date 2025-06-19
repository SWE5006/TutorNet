package com.project.tutornet.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.tutornet.dto.BookingResponseDto;
import com.project.tutornet.dto.CreateBookingRequest;
import com.project.tutornet.service.BookingService;
@RestController
@RequestMapping("/api/booking")
public class BookingController {

     @Autowired
    private BookingService bookingService;

    @PostMapping
    public  ResponseEntity<?> createBooking(@RequestBody CreateBookingRequest request) {
       bookingService.createBooking(request);
        return ResponseEntity.ok("Booking created successfully");
       
    }

    @GetMapping("/{email}")
    public ResponseEntity<List<BookingResponseDto>> getBookingsByEmail(@PathVariable("email") String email) {
        List<BookingResponseDto> bookings = bookingService.getBookingsByEmail(email);
        return ResponseEntity.ok(bookings);
    }
    
}
