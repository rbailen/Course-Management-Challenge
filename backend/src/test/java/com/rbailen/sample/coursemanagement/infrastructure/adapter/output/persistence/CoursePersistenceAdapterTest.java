package com.rbailen.sample.coursemanagement.infrastructure.adapter.output.persistence;

import com.rbailen.sample.coursemanagement.CourseUtilsTest;
import com.rbailen.sample.coursemanagement.application.port.output.CourseOutputPort;
import com.rbailen.sample.coursemanagement.domain.model.Course;
import com.rbailen.sample.coursemanagement.infrastructure.adapter.output.persistence.entity.CourseEntity;
import com.rbailen.sample.coursemanagement.infrastructure.adapter.output.persistence.mapper.CoursePersistenceMapper;
import com.rbailen.sample.coursemanagement.infrastructure.adapter.output.persistence.repository.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class CoursePersistenceAdapterTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CourseRepository courseRepository;

    private CoursePersistenceMapper coursePersistenceMapper;
    private CourseOutputPort courseOutputPort;

    private static Long COURSE_ID = null;

    @BeforeEach
    void setUp() {
        coursePersistenceMapper = Mappers.getMapper(CoursePersistenceMapper.class);
        courseOutputPort = new CoursePersistenceAdapter(courseRepository, coursePersistenceMapper);
    }

    @Test
    void saveCourse() {
        Course course = courseOutputPort.saveCourse(CourseUtilsTest.getCourse1());
        assertEquals("name", course.getName());
        assertEquals("description", course.getDescription());
    }

    @Test
    void getCourseFound() {
        givenCourses();

        Course course = courseOutputPort.getCourseById(COURSE_ID).get();
        assertEquals("name", course.getName());
        assertEquals("description", course.getDescription());
    }

    @Test
    void getCourses() {
        givenCourses();

        List<Course> courses = courseOutputPort.getCourses();
        assertEquals(2, courses.size());
    }

    @Test
    void deleteCourse() {
        givenCourses();

        courseOutputPort.deleteCourseById(COURSE_ID);
        List<Course> courses = courseOutputPort.getCourses();
        assertEquals(1, courses.size());
    }

    @Test
    void updateCourse() {
        givenCourses();

        Optional<Course> course = courseOutputPort.updateCourse(CourseUtilsTest.getCourse2());
        assertEquals("name_updated", course.get().getName());
    }

    private void givenCourses() {
        CourseEntity courseEntity1 = createCourse("name", "description", "schedule");
        entityManager.persist(courseEntity1);
        entityManager.flush();

        COURSE_ID = courseEntity1.getId();

        CourseEntity courseEntity2 = createCourse("name2", "description2", "schedule2");
        entityManager.persist(courseEntity2);
        entityManager.flush();
    }

    private CourseEntity createCourse(String name, String description, String schedule){
        return CourseEntity
                .builder()
                    .name(name)
                    .description(description)
                    .schedule(schedule)
                .build();
    }

}
