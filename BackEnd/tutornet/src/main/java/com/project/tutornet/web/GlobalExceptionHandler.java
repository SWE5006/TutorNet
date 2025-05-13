package com.project.tutornet.web;

import java.util.NoSuchElementException;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
  
  /**
   * Leverage Exception Handler frameworf for id not found Exception.
   * 
   * @param ex      NoSuchElementException
   * @param request WebRequest
   * @return http response
   */
  @SuppressWarnings("null")
  @ExceptionHandler(NoSuchElementException.class)
  public final ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException ex, WebRequest request) {
    
    ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
    return  createResponseEntity(pd, null, HttpStatus.NOT_FOUND, request);
  }

  /**
   * Leverage Exception Handler frameworf for id not found Exception.
   * 
   * @param ex      NoSuchElementException
   * @param request WebRequest
   * @return http response
   */
  @SuppressWarnings("null")
  @ExceptionHandler(ConstraintViolationException.class)
  public final ResponseEntity<Object> handleConstraintViolationException(
      ConstraintViolationException ex, WebRequest request) {
    
    ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
    return  createResponseEntity(pd, null, HttpStatus.BAD_REQUEST, request);
  }
  
  /**
   * Leverage Exception Handler frameworf for unexpected Exceptions.
   * 
   * @param ex
   * @param request
   * @return
   */
  @SuppressWarnings("null")
  @ExceptionHandler(Exception.class)
  public final ResponseEntity<Object> handleNoSuchElementException(Exception ex, WebRequest request) {
    ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    return createResponseEntity(pd, null, HttpStatus.INTERNAL_SERVER_ERROR, request);
  }
}
