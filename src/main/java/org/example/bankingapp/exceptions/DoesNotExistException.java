package org.example.bankingapp.exceptions;

public class DoesNotExistException extends RuntimeException {
  public DoesNotExistException(String message) {
    super(message);
  }
}
