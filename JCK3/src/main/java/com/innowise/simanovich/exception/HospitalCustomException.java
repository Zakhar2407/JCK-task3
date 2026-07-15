package com.innowise.simanovich.exception;

public class HospitalCustomException extends Exception {
  public HospitalCustomException() {
    super();
  }

  public HospitalCustomException(String message) {
    super(message);
  }

  public HospitalCustomException(String message, Throwable cause) {
    super(message, cause);
  }

  public HospitalCustomException(Throwable cause) {
    super(cause);
  }
}
