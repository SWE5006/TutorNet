package com.project.tutornet.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.tutornet.dto.CreateBookingDto;
import com.project.tutornet.entity.Booking;
import com.project.tutornet.service.BookingService;

@RestController
@RequestMapping("/api/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    // 学生预约时间槽
    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody CreateBookingDto dto) {
        Booking booking = bookingService.createBooking(dto);
        return ResponseEntity.ok(booking);
    }

    // 获取学生所有预约
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Booking>> getBookingsByStudentId(@PathVariable("studentId") UUID studentId) {
        List<Booking> bookings = bookingService.getBookingsByStudentId(studentId);
        return ResponseEntity.ok(bookings);
    }

    // 取消预约
    @DeleteMapping("/{bookingId}")
    public ResponseEntity<Void> cancelBooking(@PathVariable("bookingId") UUID bookingId) {
        bookingService.cancelBooking(bookingId);
        return ResponseEntity.noContent().build();
    }
}
