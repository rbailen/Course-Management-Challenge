package com.rbailen.sample.coursemanagement.application.port.input;

public interface EnrollStudentInCourseUseCase {

    void enrollStudentInCourse(Long studentId, Long courseId);

}
