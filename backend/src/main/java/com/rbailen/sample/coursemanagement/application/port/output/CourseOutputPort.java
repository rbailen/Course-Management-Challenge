package com.rbailen.sample.coursemanagement.application.port.output;

import com.rbailen.sample.coursemanagement.domain.model.Course;

import java.util.List;
import java.util.Optional;

public interface CourseOutputPort {

    Course saveCourse(Course course);

    Optional<Course> getCourseById(Long id);

    List<Course> getCourses();

    void deleteCourseById(Long id);

    Optional<Course> updateCourse(Course course);

}
