package com.project.tutornet.controller;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.tutornet.dto.BookingResponseDto;
import com.project.tutornet.dto.CreateBookingDto;
import com.project.tutornet.dto.ErrorResponse;
import com.project.tutornet.entity.Booking;
import com.project.tutornet.service.BookingService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/booking")
@CrossOrigin(origins = "*")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<?> createBooking(@Valid @RequestBody CreateBookingDto createBookingDto) {
        try {
            BookingResponseDto booking = bookingService.createBooking(createBookingDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(booking);
        } catch (BookingException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new ErrorResponse("An error occurred while creating the booking"));
        }
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<BookingResponseDto>> getBookingsByStudentId(@PathVariable UUID studentId) {
        try {
            List<Booking> bookings = bookingService.getBookingsByStudentId(studentId);
            List<BookingResponseDto> bookingDtos = bookings.stream()
                    .map(BookingResponseDto::new)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(bookingDtos);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/student/{studentId}/status/{status}")
    public ResponseEntity<List<BookingResponseDto>> getBookingsByStudentIdAndStatus(
            @PathVariable UUID studentId,
            @PathVariable String status) {
        try {
            List<Booking> bookings = bookingService.getBookingsByStudentIdAndStatus(studentId, status);
            List<BookingResponseDto> bookingDtos = bookings.stream()
                    .map(BookingResponseDto::new)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(bookingDtos);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<?> getBookingById(@PathVariable UUID bookingId) {
        try {
            return bookingService.getBookingById(bookingId)
                    .map(booking -> ResponseEntity.ok(new BookingResponseDto(booking)))
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{bookingId}/cancel")
    public ResponseEntity<?> cancelBooking(@PathVariable UUID bookingId) {
        try {
            BookingResponseDto booking = bookingService.cancelBooking(bookingId);
            return ResponseEntity.ok(booking);
        } catch (BookingException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new ErrorResponse("An error occurred while cancelling the booking"));
        }
    }

}
