package com.rbailen.sample.coursemanagement.application.service;

import com.rbailen.sample.coursemanagement.application.port.input.*;
import com.rbailen.sample.coursemanagement.application.port.output.CourseOutputPort;
import com.rbailen.sample.coursemanagement.domain.exception.CourseNotFound;
import com.rbailen.sample.coursemanagement.domain.model.Course;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class CourseService implements CreateCourseUseCase, GetCourseUseCase, GetCoursesUseCase, DeleteCourseUseCase, UpdateCourseUseCase {

    private final CourseOutputPort courseOutputPort;

    @Override
    public Course createCourse(Course course) {
        course = courseOutputPort.saveCourse(course);
        return course;
    }

    @Override
    public Course getCourseById(Long id) {
        return courseOutputPort.getCourseById(id).orElseThrow(() -> new CourseNotFound("Course not found with id " + id));
    }

    @Override
    public List<Course> getCourses() {
        return courseOutputPort.getCourses();
    }

    @Override
    public void deleteCourseById(Long id) {
        getCourseById(id);

        courseOutputPort.deleteCourseById(id);
    }

    @Override
    public Course updateCourse(Course course) {
        return courseOutputPort.updateCourse(course).orElseThrow(() -> new CourseNotFound("Course not found with id " + course.getId()));
    }

}
