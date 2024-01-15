package com.rbailen.sample.coursemanagement.application.port.input;

import com.rbailen.sample.coursemanagement.domain.model.Course;

import java.util.List;

public interface GetCoursesUseCase {

    List<Course> getCourses();

}
