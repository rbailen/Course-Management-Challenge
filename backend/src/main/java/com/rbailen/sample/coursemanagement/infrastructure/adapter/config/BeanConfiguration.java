package com.rbailen.sample.coursemanagement.infrastructure.adapter.config;

import com.rbailen.sample.coursemanagement.application.service.CourseService;
import com.rbailen.sample.coursemanagement.application.service.StudentCourseService;
import com.rbailen.sample.coursemanagement.infrastructure.adapter.output.eventpublisher.StudentEventPublisherAdapter;
import com.rbailen.sample.coursemanagement.infrastructure.adapter.output.persistence.StudentCoursePersistenceAdapter;
import com.rbailen.sample.coursemanagement.infrastructure.adapter.output.persistence.StudentPersistenceAdapter;
import com.rbailen.sample.coursemanagement.infrastructure.adapter.output.persistence.mapper.CoursePersistenceMapper;
import com.rbailen.sample.coursemanagement.infrastructure.adapter.output.persistence.mapper.StudentPersistenceMapper;
import com.rbailen.sample.coursemanagement.infrastructure.adapter.output.persistence.repository.CourseRepository;
import com.rbailen.sample.coursemanagement.infrastructure.adapter.output.persistence.repository.StudentRepository;
import com.rbailen.sample.coursemanagement.infrastructure.adapter.output.persistence.CoursePersistenceAdapter;
import com.rbailen.sample.coursemanagement.infrastructure.adapter.output.persistence.repository.StudentCourseRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public CoursePersistenceAdapter coursePersistenceAdapter(CourseRepository courseRepository, CoursePersistenceMapper coursePersistenceMapper) {
        return new CoursePersistenceAdapter(courseRepository, coursePersistenceMapper);
    }

    @Bean
    public StudentPersistenceAdapter studentPersistenceAdapter(StudentRepository studentRepository, StudentPersistenceMapper studentPersistenceMapper) {
        return new StudentPersistenceAdapter(studentRepository, studentPersistenceMapper);
    }

    @Bean
    public StudentCoursePersistenceAdapter studentCoursePersistenceAdapter(StudentRepository studentRepository, CourseRepository courseRepository,
                                                                           StudentCourseRepository studentCourseRepository) {
        return new StudentCoursePersistenceAdapter(studentRepository, courseRepository, studentCourseRepository);
    }

    @Bean
    public StudentEventPublisherAdapter studentEventPublisherAdapter(ApplicationEventPublisher applicationEventPublisher) {
        return new StudentEventPublisherAdapter(applicationEventPublisher);
    }

    @Bean
    public CourseService courseService(CoursePersistenceAdapter coursePersistenceAdapter) {
        return new CourseService(coursePersistenceAdapter);
    }

    @Bean
    public StudentCourseService studentCourseService(StudentPersistenceAdapter studentPersistenceAdapter,
                                                     StudentCoursePersistenceAdapter studentCoursePersistenceAdapter,
                                                     StudentEventPublisherAdapter studentEventPublisherAdapter) {
        return new StudentCourseService(studentPersistenceAdapter, studentCoursePersistenceAdapter, studentEventPublisherAdapter);
    }

}
