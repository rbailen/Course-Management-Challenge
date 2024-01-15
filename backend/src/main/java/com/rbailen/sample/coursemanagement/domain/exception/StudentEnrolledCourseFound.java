package com.rbailen.sample.coursemanagement.domain.exception;

public class StudentEnrolledCourseFound extends RuntimeException {

    public StudentEnrolledCourseFound(String message) {
        super(message);
    }

}
