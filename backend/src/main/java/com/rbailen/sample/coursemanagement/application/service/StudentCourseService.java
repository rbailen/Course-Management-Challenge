package com.rbailen.sample.coursemanagement.application.service;

import com.rbailen.sample.coursemanagement.application.port.input.EnrollStudentInCourseUseCase;
import com.rbailen.sample.coursemanagement.application.port.output.StudentCourseOutputPort;
import com.rbailen.sample.coursemanagement.application.port.output.StudentEventPublisher;
import com.rbailen.sample.coursemanagement.application.port.output.StudentOutputPort;
import com.rbailen.sample.coursemanagement.domain.event.StudentEnrolledEvent;
import com.rbailen.sample.coursemanagement.domain.exception.StudentNotFound;
import com.rbailen.sample.coursemanagement.domain.model.Student;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StudentCourseService implements EnrollStudentInCourseUseCase {

    private final StudentOutputPort studentOutputPort;

    private final StudentCourseOutputPort studentCourseOutputPort;

    private final StudentEventPublisher studentEventPublisher;

    @Override
    public void enrollStudentInCourse(Long studentId, Long courseId) {

        Student student = studentOutputPort.getStudentById(studentId).orElseThrow(() -> new StudentNotFound("Student not found with id " + courseId));

        studentCourseOutputPort.enrollStudentInCourse(student.getId(), courseId);

        studentEventPublisher.publishStudentEnrolledEvent(new StudentEnrolledEvent(studentId, courseId,  student.getEmail()));
    }

}
