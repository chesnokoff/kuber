package org.example.exception;

public class LogServiceException extends RuntimeException {
  public LogServiceException(String message, Throwable cause) {
    super(message, cause);
  }

  public LogServiceException(String message) {
    super(message);
  }
}

