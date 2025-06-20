package com.project.tutornet.dto;

import java.util.Date;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponseDto {
    private UUID bookingId;
    private Date bookingDate;
    private String bookingStatus;
    private String subjectName;
    private int numberOfBooking;
    private String studentName;
    private String Timeslot;
    private String tutorName;

    public BookingResponseDto(UUID bookingId, Date bookingDate, String bookingStatus, String subjectName, int numberOfBooking) {
        this.bookingId = bookingId;
        this.bookingDate = bookingDate;
        this.bookingStatus = bookingStatus;
        this.subjectName = subjectName;
        this.numberOfBooking = numberOfBooking;
    }
}