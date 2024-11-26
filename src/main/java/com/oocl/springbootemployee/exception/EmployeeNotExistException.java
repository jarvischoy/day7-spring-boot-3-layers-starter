package com.oocl.springbootemployee.exception;

public class EmployeeNotExistException extends RuntimeException {
    public EmployeeNotExistException(String message) {
        super(message);
    }
}
