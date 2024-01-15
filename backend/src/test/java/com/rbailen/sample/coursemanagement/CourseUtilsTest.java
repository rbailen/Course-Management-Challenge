package com.rbailen.sample.coursemanagement;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rbailen.sample.coursemanagement.domain.model.Course;

import java.util.Arrays;
import java.util.List;

public class CourseUtilsTest {

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Course getCourse1() {
        return createCourse(1L, "name", "description", "schedule");
    }

    public static Course getCourse2() {
        return createCourse(1L, "name_updated", "description", "schedule");
    }

    public static Course getCourse3() {
        return createCourse("name", "description", "schedule");
    }

    public static List<Course> getCourses() {
        return Arrays.asList(
                createCourse(1L, "name", "description", "schedule"),
                createCourse(2L, "name2", "description2", "schedule2")
        );
    }

    private static Course createCourse(Long id, String name, String description, String schedule){
        return Course.builder().id(id).name(name).description(description).schedule(schedule).build();
    }

    private static Course createCourse(String name, String description, String schedule){
        return Course.builder().name(name).description(description).schedule(schedule).build();
    }

}
