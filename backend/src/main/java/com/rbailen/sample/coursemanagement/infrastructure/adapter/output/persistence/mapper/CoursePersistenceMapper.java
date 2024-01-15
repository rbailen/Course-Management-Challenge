package com.rbailen.sample.coursemanagement.infrastructure.adapter.output.persistence.mapper;

import com.rbailen.sample.coursemanagement.domain.model.Course;
import com.rbailen.sample.coursemanagement.infrastructure.adapter.output.persistence.entity.CourseEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface CoursePersistenceMapper {

    CourseEntity toCourseEntity(Course course);

    Course toCourse(CourseEntity courseEntity);

    List<Course> toCourseList (List<CourseEntity> courseEntities);

}
