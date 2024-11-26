package com.oocl.springbootemployee.exception;

public class EmployeeSalaryNotValidException extends RuntimeException {
    public EmployeeSalaryNotValidException(String message) {
        super(message);
    }
}
