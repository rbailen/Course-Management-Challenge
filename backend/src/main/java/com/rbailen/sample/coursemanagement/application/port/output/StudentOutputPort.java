package com.rbailen.sample.coursemanagement.application.port.output;

import com.rbailen.sample.coursemanagement.domain.model.Student;

import java.util.Optional;

public interface StudentOutputPort {

    Optional<Student> getStudentById(Long id);

}
