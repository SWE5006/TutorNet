package com.project.tutornet.service;


import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.tutornet.controller.BookingException;
import com.project.tutornet.dto.BookingResponseDto;
import com.project.tutornet.dto.CreateBookingDto;
import com.project.tutornet.dto.UpdateBookingStatusDto;
import com.project.tutornet.entity.AvailableSlot;
import com.project.tutornet.entity.Booking;
import com.project.tutornet.entity.Student;
import com.project.tutornet.repository.AvailableSlotRepository;
import com.project.tutornet.repository.BookingRepository;
import com.project.tutornet.repository.StudentRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class BookingService {

  @Autowired
  private BookingRepository bookingRepository;

  @Autowired
  private StudentRepository studentRepository;

  @Autowired
  private AvailableSlotRepository availableSlotRepository;

  @Transactional
  public BookingResponseDto createBooking(CreateBookingDto createBookingDto) {
    // Validate student exists
    Optional<Student> studentOpt = studentRepository.findById(
      createBookingDto.getStudentId()
    );
    if (!studentOpt.isPresent()) {
      throw new BookingException(
        "Student not found with ID: " + createBookingDto.getStudentId()
      );
    }

    // Validate slot exists and is available
    Optional<AvailableSlot> slotOpt = availableSlotRepository.findById(
      createBookingDto.getSlotId()
    );
    if (!slotOpt.isPresent()) {
      throw new BookingException(
        "Available slot not found with ID: " + createBookingDto.getSlotId()
      );
    }

    AvailableSlot slot = slotOpt.get();
    if (!"AVAILABLE".equalsIgnoreCase(slot.getSlotStatus())) {
      throw new BookingException("Selected slot is not available for booking");
    }

    // Create new booking
    Booking booking = new Booking();
    booking.setStudent(studentOpt.get().getUserInfo());
    booking.setSlot(slot);
    booking.setSubjectName(createBookingDto.getSubjectName());
    booking.setBookingStatus(createBookingDto.getBookingStatus());
    booking.setBookingDate(new Date());
    booking.setScheduleStart(slot.getScheduleStart());
    booking.setScheduleEnd(slot.getScheduleEnd());

    // Save booking
    Booking savedBooking = bookingRepository.save(booking);

    // Update slot status to booked
    slot.setSlotStatus("BOOKED");
    availableSlotRepository.save(slot);

    return new BookingResponseDto(savedBooking);
  }

  @Transactional
  public BookingResponseDto updateBookingStatus(
    UUID bookingId,
    UpdateBookingStatusDto updateStatusDto
  ) {
    Optional<Booking> bookingOpt = bookingRepository.findById(bookingId);
    if (!bookingOpt.isPresent()) {
      throw new BookingException("Booking not found with ID: " + bookingId);
    }

    Booking booking = bookingOpt.get();
    String oldStatus = booking.getBookingStatus();
    String newStatus = updateStatusDto.getBookingStatus();

    // Validate status transition
    if (!isValidStatusTransition(oldStatus, newStatus)) {
      throw new BookingException(
        "Invalid status transition from " + oldStatus + " to " + newStatus
      );
    }

    // Update booking status
    booking.setBookingStatus(newStatus);
    Booking savedBooking = bookingRepository.save(booking);

    // Handle slot status changes based on booking status
    handleSlotStatusChange(booking, oldStatus, newStatus);

    return new BookingResponseDto(savedBooking);
  }

  public List<Booking> getBookingsByStudentId(UUID studentId) {
    return bookingRepository.findByStudent_Id(studentId);
  }

  public List<Booking> getBookingsByStudentIdAndStatus(
    UUID studentId,
    String status
  ) {
    return bookingRepository.findByStudent_IdAndBookingStatus(
      studentId,
      status
    );
  }

  public Optional<Booking> getBookingById(UUID bookingId) {
    return bookingRepository.findById(bookingId);
  }

  @Transactional
  public BookingResponseDto cancelBooking(UUID bookingId) {
    Optional<Booking> bookingOpt = bookingRepository.findById(bookingId);
    if (!bookingOpt.isPresent()) {
      throw new BookingException("Booking not found with ID: " + bookingId);
    }

    Booking booking = bookingOpt.get();
    if ("CANCELLED".equalsIgnoreCase(booking.getBookingStatus())) {
      throw new BookingException("Booking is already cancelled");
    }

    // Update booking status
    booking.setBookingStatus("CANCELLED");
    Booking savedBooking = bookingRepository.save(booking);

    // Make slot available again
    AvailableSlot slot = booking.getSlot();
    slot.setSlotStatus("AVAILABLE");
    availableSlotRepository.save(slot);

    return new BookingResponseDto(savedBooking);
  }

  private boolean isValidStatusTransition(String oldStatus, String newStatus) {
    // Define valid status transitions based on your business rules
    switch (oldStatus.toUpperCase()) {
      case "PENDING":
        return (
          newStatus.equalsIgnoreCase("CONFIRMED") ||
          newStatus.equalsIgnoreCase("CANCELLED") ||
          newStatus.equalsIgnoreCase("REJECTED")
        );
      case "CONFIRMED":
        return (
          newStatus.equalsIgnoreCase("COMPLETED") ||
          newStatus.equalsIgnoreCase("CANCELLED") ||
          newStatus.equalsIgnoreCase("NO_SHOW")
        );
      case "COMPLETED":
        return false; // Completed bookings cannot be changed
      case "CANCELLED":
        return newStatus.equalsIgnoreCase("CONFIRMED"); // Allow reactivation
      case "REJECTED":
        return newStatus.equalsIgnoreCase("PENDING"); // Allow resubmission
      case "NO_SHOW":
        return newStatus.equalsIgnoreCase("COMPLETED"); // Allow manual completion
      default:
        return true; // Allow any transition for unknown statuses
    }
  }

  private void handleSlotStatusChange(
    Booking booking,
    String oldStatus,
    String newStatus
  ) {
    AvailableSlot slot = booking.getSlot();

    // If booking is cancelled or rejected, make slot available
    if (
      newStatus.equalsIgnoreCase("CANCELLED") ||
      newStatus.equalsIgnoreCase("REJECTED")
    ) {
      slot.setSlotStatus("AVAILABLE");
    }
    // If booking is confirmed from pending/rejected, mark slot as booked
    else if (
      newStatus.equalsIgnoreCase("CONFIRMED") &&
      (
        oldStatus.equalsIgnoreCase("PENDING") ||
        oldStatus.equalsIgnoreCase("REJECTED")
      )
    ) {
      slot.setSlotStatus("BOOKED");
    }
    // If booking is completed, mark slot as completed
    else if (newStatus.equalsIgnoreCase("COMPLETED")) {
      slot.setSlotStatus("COMPLETED");
    }

    availableSlotRepository.save(slot);
  }
}
