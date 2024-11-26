package com.oocl.springbootemployee.exception;

public class EmployeeAgeNotValidException extends RuntimeException {
    public EmployeeAgeNotValidException(String message) {
        super(message);
    }
}
