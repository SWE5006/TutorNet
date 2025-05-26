package com.project.tutornet.web;

// Custom Exception for business logic errors
public class BookingException extends RuntimeException {
    public BookingException(String message) {
        super(message);
    }
}
