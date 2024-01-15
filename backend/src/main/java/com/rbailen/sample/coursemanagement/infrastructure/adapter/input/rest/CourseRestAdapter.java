package com.rbailen.sample.coursemanagement.infrastructure.adapter.input.rest;

import com.rbailen.sample.coursemanagement.application.port.input.*;
import com.rbailen.sample.coursemanagement.domain.model.Course;
import com.rbailen.sample.coursemanagement.infrastructure.adapter.input.rest.data.request.CourseCreateRequest;
import com.rbailen.sample.coursemanagement.infrastructure.adapter.input.rest.data.request.CourseUpdateRequest;
import com.rbailen.sample.coursemanagement.infrastructure.adapter.input.rest.data.response.CourseCreateResponse;
import com.rbailen.sample.coursemanagement.infrastructure.adapter.input.rest.data.response.CourseQueryResponse;
import com.rbailen.sample.coursemanagement.infrastructure.adapter.input.rest.data.response.CourseUpdateResponse;
import com.rbailen.sample.coursemanagement.infrastructure.adapter.input.rest.data.response.CoursesQueryResponse;
import com.rbailen.sample.coursemanagement.infrastructure.adapter.input.rest.mapper.CourseRestMapper;
import io.micrometer.tracing.Tracer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/v1")
@RequiredArgsConstructor
public class CourseRestAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(CourseRestAdapter.class);

    private final CreateCourseUseCase createCourseUseCase;

    private final GetCourseUseCase getCourseUseCase;

    private final GetCoursesUseCase getCoursesUseCase;

    private final DeleteCourseUseCase deleteCourseUseCase;

    private final UpdateCourseUseCase updateCourseUseCase;

    private final EnrollStudentInCourseUseCase enrollStudentInCourseUseCase;

    private final CourseRestMapper courseRestMapper;

    private final Tracer tracer;

    @Operation(summary = "Create a course")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Course created",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CourseCreateResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class))})
    })
    @PostMapping(value = "/courses")
    public ResponseEntity<CourseCreateResponse> createCourse(@RequestBody @Valid CourseCreateRequest courseCreateRequest){
        String traceId = tracer.currentSpan().context().traceId();
        LOG.info("Beginning of the process with traceId {} for the creation of a new course", traceId);

        Course course = courseRestMapper.toCourse(courseCreateRequest);
        course = createCourseUseCase.createCourse(course);

        LOG.info("Completion of the process with traceId {} for the creation of a new course", traceId);

        return new ResponseEntity<>(courseRestMapper.toCourseCreateResponse(course), HttpStatus.CREATED);
    }

    @Operation(summary = "Return a course by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Course found",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CourseQueryResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Course not found",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class))})
    })
    @GetMapping(value = "/courses/{id}")
    public ResponseEntity<CourseQueryResponse> getCourseById(
            @Parameter(required = true, name = "id", description = "Course id", example = "35455")
            @PathVariable Long id)
    {
        String traceId = tracer.currentSpan().context().traceId();
        LOG.info("Beginning of the process with traceId {} to get the course with id {} ", traceId, id);

        Course course = getCourseUseCase.getCourseById(id);

        LOG.info("Completion of the process with traceId {} to get the course with id {} ", traceId, id);

        return new ResponseEntity<>(courseRestMapper.toCourseQueryResponse(course), HttpStatus.OK);
    }

    @Operation(summary = "Return all courses")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of courses",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = CoursesQueryResponse.class)))})
    })
    @GetMapping(value = "/courses")
    public ResponseEntity<List<CoursesQueryResponse>> getCourses()
    {
        String traceId = tracer.currentSpan().context().traceId();
        LOG.info("Beginning of the process with traceId {} to get all the courses", traceId);

        List<Course> courses = getCoursesUseCase.getCourses();

        LOG.info("Completion of the process with traceId {} to get all the courses", traceId);

        return new ResponseEntity<>(courseRestMapper.toCourseQueryResponseList(courses), HttpStatus.OK);
    }

    @Operation(summary = "Delete a course by id")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Course deleted"),
            @ApiResponse(responseCode = "404", description = "Course not found",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class))})
    })
    @DeleteMapping(value = "/courses/{id}")
    public ResponseEntity<Void> deleteCourse(
            @Parameter(required = true, name = "id", description = "Course id", example = "35455")
            @PathVariable Long id)
    {
        String traceId = tracer.currentSpan().context().traceId();
        LOG.info("Beginning of the process with traceId {} to delete the course with id {}", traceId, id);

        deleteCourseUseCase.deleteCourseById(id);

        LOG.info("Completion of the process with traceId {} to delete the course with id {}", traceId, id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Update a course")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Course updated",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CourseCreateResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class))}),
            @ApiResponse(responseCode = "404", description = "Course not found",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class))})
    })
    @PutMapping(value = "/courses/{id}")
    public ResponseEntity<CourseUpdateResponse> updateCourse(
            @Parameter(required = true, name = "id", description = "Course id", example = "35455")
            @PathVariable Long id,

            @RequestBody @Valid CourseUpdateRequest courseUpdateRequest
    ){
        String traceId = tracer.currentSpan().context().traceId();
        LOG.info("Beginning of the process with traceId {} for the update of the course with id {}", traceId, id);

        courseUpdateRequest.setId(id);
        Course course = courseRestMapper.toCourse(courseUpdateRequest);
        course = updateCourseUseCase.updateCourse(course);

        LOG.info("Completion of the process with traceId {} for the update of the course with id {}", traceId, id);

        return new ResponseEntity<>(courseRestMapper.toCourseUpdateResponse(course), HttpStatus.OK);
    }

    @Operation(summary = "Enroll a student in a course")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Student enrolled",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CourseCreateResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Course not found",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class))}),
            @ApiResponse(responseCode = "409", description = "Student already enrolled in course",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class))})
    })
    @PostMapping(value = "/courses/{courseId}/students/{studentId}")
    public ResponseEntity<Void> enrollStudentInCourse(
            @Parameter(required = true, name = "courseId", description = "Course id", example = "35455")
            @PathVariable Long courseId,

            @Parameter(required = true, name = "studentId", description = "Student id", example = "35455")
            @PathVariable Long studentId)
    {
        String traceId = tracer.currentSpan().context().traceId();
        LOG.info("Beginning of the process with traceId {} for the enrollment of the student with id {} in the course {}", traceId, studentId, courseId);

        enrollStudentInCourseUseCase.enrollStudentInCourse(studentId, courseId);

        LOG.info("Completion of the process with traceId {} for the enrollment of the student with id {} in the course {}", traceId, studentId, courseId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
