package com.project.tutornet.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.tutornet.dto.CreateBookingDto;
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

    // 学生预约时间槽
    public Booking createBooking(CreateBookingDto dto) {
        Student student = studentRepository.findById(dto.getStudentId())
            .orElseThrow(() -> new RuntimeException("Student not found"));

        TimeSlot slot = timeSlotRepository.findById(dto.getSlotId())
            .orElseThrow(() -> new RuntimeException("TimeSlot not found"));

        if (!"AVAILABLE".equalsIgnoreCase(slot.getStatus())) {
            throw new RuntimeException("TimeSlot is not available");
        }

        Booking booking = new Booking();
        booking.setStudent(student);
        booking.setSlot(slot);
        booking.setSubjectName(dto.getSubjectName());
        booking.setBookingStatus(dto.getBookingStatus() != null ? dto.getBookingStatus() : "PENDING");
        booking.setBookingDate(new Date());

        // 更新时间槽状态
        slot.setStatus("BOOKED");
        timeSlotRepository.save(slot);

        return bookingRepository.save(booking);
    }

    // 获取学生所有预约
    public List<Booking> getBookingsByStudentId(UUID studentId) {
        return bookingRepository.findByStudent_Id(studentId);
    }

    // 取消预约
    public void cancelBooking(UUID bookingId) {
        Optional<Booking> bookingOpt = bookingRepository.findById(bookingId);
        if (bookingOpt.isPresent()) {
            Booking booking = bookingOpt.get();
            // 释放时间槽
            TimeSlot slot = booking.getSlot();
            if (slot != null) {
                slot.setStatus("AVAILABLE");
                timeSlotRepository.save(slot);
            }
            // 删除预约
            bookingRepository.deleteById(bookingId);
        } else {
            throw new RuntimeException("Booking not found");
        }
    }
}
