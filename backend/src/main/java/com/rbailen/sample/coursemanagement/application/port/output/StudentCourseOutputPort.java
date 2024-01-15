package com.rbailen.sample.coursemanagement.application.port.output;

public interface StudentCourseOutputPort {

    void enrollStudentInCourse(Long studentId, Long courseId);

}
