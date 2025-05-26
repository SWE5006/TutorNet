package com.project.tutornet.dto;

import java.util.Date;
import java.util.UUID;

import com.project.tutornet.model.Booking;

public class BookingResponseDto {
private UUID bookingId;
    private Date bookingDate;
    private Date scheduleStart;
    private Date scheduleEnd;
    private String bookingStatus;
    private String subjectName;
    private UUID studentId;
    private String studentName;
    private UUID tutorId;
    private String tutorName;
    private UUID slotId;
    
    // Constructor from Booking entity
    public BookingResponseDto(Booking booking) {
        this.bookingId = booking.getBookingId();
        this.bookingDate = booking.getBookingDate();
        this.scheduleStart = booking.getScheduleStart();
        this.scheduleEnd = booking.getScheduleEnd();
        this.bookingStatus = booking.getBookingStatus();
        this.subjectName = booking.getSubjectName();
        this.studentId = booking.getStudent().getUserId();
        this.studentName = booking.getStudent().getFirstName() + " " + booking.getStudent().getLastName();
        this.slotId = booking.getSlot().getAvailableId();
        if (booking.getSlot().getTutors() != null) {
            this.tutorId = booking.getSlot().getTutors().getUserId();
            this.tutorName = booking.getSlot().getTutors().getFirstName() + " " + booking.getSlot().getTutors().getLastName();
        }
    }
    
    // Getters and Setters
    public UUID getBookingId() { return bookingId; }
    public void setBookingId(UUID bookingId) { this.bookingId = bookingId; }
    
    public Date getBookingDate() { return bookingDate; }
    public void setBookingDate(Date bookingDate) { this.bookingDate = bookingDate; }
    
    public Date getScheduleStart() { return scheduleStart; }
    public void setScheduleStart(Date scheduleStart) { this.scheduleStart = scheduleStart; }
    
    public Date getScheduleEnd() { return scheduleEnd; }
    public void setScheduleEnd(Date scheduleEnd) { this.scheduleEnd = scheduleEnd; }
    
    public String getBookingStatus() { return bookingStatus; }
    public void setBookingStatus(String bookingStatus) { this.bookingStatus = bookingStatus; }
    
    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }
    
    public UUID getStudentId() { return studentId; }
    public void setStudentId(UUID studentId) { this.studentId = studentId; }
    
    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
    
    public UUID getTutorId() { return tutorId; }
    public void setTutorId(UUID tutorId) { this.tutorId = tutorId; }
    
    public String getTutorName() { return tutorName; }
    public void setTutorName(String tutorName) { this.tutorName = tutorName; }
    
    public UUID getSlotId() { return slotId; }
    public void setSlotId(UUID slotId) { this.slotId = slotId; }
}
