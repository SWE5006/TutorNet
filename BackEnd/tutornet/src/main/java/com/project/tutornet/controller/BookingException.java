package com.project.tutornet.controller;

// Custom Exception for business logic errors
public class BookingException extends RuntimeException {

  public BookingException(String message) {
    super(message);
  }
}
