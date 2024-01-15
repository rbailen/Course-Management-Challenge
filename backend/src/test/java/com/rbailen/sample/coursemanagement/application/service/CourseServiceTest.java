package com.rbailen.sample.coursemanagement.application.service;

import com.rbailen.sample.coursemanagement.CourseUtilsTest;
import com.rbailen.sample.coursemanagement.application.port.input.*;
import com.rbailen.sample.coursemanagement.application.port.output.CourseOutputPort;
import com.rbailen.sample.coursemanagement.domain.model.Course;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CourseServiceTest {

    private CreateCourseUseCase createCourseUseCase;

    private GetCourseUseCase getCourseUseCase;

    private GetCoursesUseCase coursesUseCase;

    private UpdateCourseUseCase updateCourseUseCase;

    private CourseOutputPort courseOutputPort;

    @BeforeEach
    void setUp() {
        courseOutputPort = mock(CourseOutputPort.class);

        createCourseUseCase = new CourseService(courseOutputPort);
        getCourseUseCase = new CourseService(courseOutputPort);
        coursesUseCase = new CourseService(courseOutputPort);
        updateCourseUseCase = new CourseService(courseOutputPort);
    }

    @Test
    void createCourse() {
        Course expected = CourseUtilsTest.getCourse1();

        Course course = Course.builder().id(1L).name("name").description("description").schedule("schedule").build();
        when(courseOutputPort.saveCourse(course)).thenReturn(expected);

        Course created = createCourseUseCase.createCourse(course);

        assertEquals(expected, created);
    }

    @Test
    void getCourse() {
        Course expected = CourseUtilsTest.getCourse1();

        when(courseOutputPort.getCourseById(1L)).thenReturn(Optional.of(expected));

        Course found = getCourseUseCase.getCourseById(1L);

        assertEquals(expected, found);
    }

    @Test
    void getCourses() {
        List<Course> expected = CourseUtilsTest.getCourses();

        when(courseOutputPort.getCourses()).thenReturn(expected);

        List<Course> found = coursesUseCase.getCourses();

        assertEquals(expected, found);
    }

    @Test
    void updateCourse() {
        Course expected = CourseUtilsTest.getCourse2();

        Course course = Course.builder().id(1L).name("name_updated").description("description").schedule("schedule").build();
        when(courseOutputPort.updateCourse(course)).thenReturn(Optional.of(expected));

        updateCourseUseCase.updateCourse(course);

        assertEquals(expected.getName(), course.getName());
    }

}
