package com.project.tutornet.dto;

import jakarta.validation.constraints.NotBlank;

public class UpdateBookingStatusDto {
 @NotBlank(message = "Booking status is required")
    private String bookingStatus;
    
    // Constructors
    public UpdateBookingStatusDto() {}
    
    public UpdateBookingStatusDto(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }
    
    // Getters and Setters
    public String getBookingStatus() { return bookingStatus; }
    public void setBookingStatus(String bookingStatus) { this.bookingStatus = bookingStatus; }
}
