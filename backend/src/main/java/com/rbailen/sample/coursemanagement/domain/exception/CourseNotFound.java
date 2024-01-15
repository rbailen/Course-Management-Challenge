package com.rbailen.sample.coursemanagement.domain.exception;

public class CourseNotFound extends RuntimeException {

    public CourseNotFound(String message) {
        super(message);
    }

}
