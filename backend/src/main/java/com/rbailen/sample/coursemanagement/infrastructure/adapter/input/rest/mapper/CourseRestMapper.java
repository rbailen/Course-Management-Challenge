package com.rbailen.sample.coursemanagement.infrastructure.adapter.input.rest.mapper;

import com.rbailen.sample.coursemanagement.domain.model.Course;
import com.rbailen.sample.coursemanagement.infrastructure.adapter.input.rest.data.request.CourseCreateRequest;
import com.rbailen.sample.coursemanagement.infrastructure.adapter.input.rest.data.response.CoursesQueryResponse;
import com.rbailen.sample.coursemanagement.infrastructure.adapter.input.rest.data.request.CourseUpdateRequest;
import com.rbailen.sample.coursemanagement.infrastructure.adapter.input.rest.data.response.CourseCreateResponse;
import com.rbailen.sample.coursemanagement.infrastructure.adapter.input.rest.data.response.CourseQueryResponse;
import com.rbailen.sample.coursemanagement.infrastructure.adapter.input.rest.data.response.CourseUpdateResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface CourseRestMapper {

    Course toCourse(CourseCreateRequest courseCreateRequest);

    CourseCreateResponse toCourseCreateResponse(Course course);

    CourseQueryResponse toCourseQueryResponse(Course course);

    List<CoursesQueryResponse> toCourseQueryResponseList(List<Course> courses);

    Course toCourse(CourseUpdateRequest courseUpdateRequest);

    CourseUpdateResponse toCourseUpdateResponse(Course course);

}
