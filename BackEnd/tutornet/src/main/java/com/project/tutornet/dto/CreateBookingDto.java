package com.project.tutornet.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateBookingDto {
    @NotNull(message = "Student ID is required")
    private UUID studentId;
    
    @NotNull(message = "Slot ID is required")
    private UUID slotId;
    
    @NotBlank(message = "Subject name is required")
    private String subjectName;
    
    private String bookingStatus = "PENDING"; // Default status
    
    // Constructors
    public CreateBookingDto() {}
    
    public CreateBookingDto(UUID studentId, UUID slotId, String subjectName) {
        this.studentId = studentId;
        this.slotId = slotId;
        this.subjectName = subjectName;
    }
    
    // Getters and Setters
    public UUID getStudentId() { return studentId; }
    public void setStudentId(UUID studentId) { this.studentId = studentId; }
    
    public UUID getSlotId() { return slotId; }
    public void setSlotId(UUID slotId) { this.slotId = slotId; }
    
    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }
    
    public String getBookingStatus() { return bookingStatus; }
    public void setBookingStatus(String bookingStatus) { this.bookingStatus = bookingStatus; }
}
