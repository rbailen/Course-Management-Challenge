package com.rbailen.sample.coursemanagement.domain.exception;

public class StudentNotFound extends RuntimeException {

    public StudentNotFound(String message) {
        super(message);
    }

}
