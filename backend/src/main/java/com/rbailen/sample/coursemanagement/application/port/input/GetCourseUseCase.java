package com.rbailen.sample.coursemanagement.application.port.input;

import com.rbailen.sample.coursemanagement.domain.model.Course;

public interface GetCourseUseCase {

    Course getCourseById(Long id);

}
