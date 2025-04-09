package org.example.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(LogServiceException.class)
  public ResponseEntity<String> handleLogServiceException(LogServiceException ex) {
    return ResponseEntity.internalServerError()
        .body("Server error while handling logs: " + ex.getMessage());
  }
}

