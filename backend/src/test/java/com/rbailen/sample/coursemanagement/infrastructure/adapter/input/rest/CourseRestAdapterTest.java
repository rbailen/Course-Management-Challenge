package com.rbailen.sample.coursemanagement.infrastructure.adapter.input.rest;

import com.rbailen.sample.coursemanagement.CourseUtilsTest;
import com.rbailen.sample.coursemanagement.application.port.input.*;
import com.rbailen.sample.coursemanagement.domain.model.Course;
import com.rbailen.sample.coursemanagement.infrastructure.adapter.input.rest.data.request.CourseCreateRequest;
import com.rbailen.sample.coursemanagement.infrastructure.adapter.input.rest.data.request.CourseUpdateRequest;
import com.rbailen.sample.coursemanagement.infrastructure.adapter.input.rest.mapper.CourseRestMapper;
import io.micrometer.tracing.Span;
import io.micrometer.tracing.TraceContext;
import io.micrometer.tracing.Tracer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.UUID;

import static com.rbailen.sample.coursemanagement.CourseUtilsTest.asJsonString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class CourseRestAdapterTest {

    private CreateCourseUseCase createCourseUseCase;

    private GetCourseUseCase getCourseUseCase;

    private GetCoursesUseCase getCoursesUseCase;

    private DeleteCourseUseCase deleteCourseUseCase;

    private UpdateCourseUseCase updateCourseUseCase;

    private EnrollStudentInCourseUseCase enrollStudentInCourseUseCase;

    private CourseRestMapper courseRestMapper;

    private Tracer tracer;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        createCourseUseCase = mock(CreateCourseUseCase.class);
        getCourseUseCase = mock(GetCourseUseCase.class);
        getCoursesUseCase = mock(GetCoursesUseCase.class);
        deleteCourseUseCase = mock(DeleteCourseUseCase.class);
        updateCourseUseCase = mock(UpdateCourseUseCase.class);
        enrollStudentInCourseUseCase = mock(EnrollStudentInCourseUseCase.class);

        courseRestMapper = mock(CourseRestMapper.class);

        tracer = mock(Tracer.class);
        Span span = mock(Span.class);
        TraceContext traceContext = mock(TraceContext.class);
        when(tracer.currentSpan()).thenReturn(span);
        when(tracer.currentSpan().context()).thenReturn(traceContext);
        when(tracer.currentSpan().context().traceId()).thenReturn(UUID.randomUUID().toString());

        CourseRestAdapter courseRestAdapter = new CourseRestAdapter(createCourseUseCase, getCourseUseCase, getCoursesUseCase, deleteCourseUseCase,
                updateCourseUseCase, enrollStudentInCourseUseCase, courseRestMapper, tracer);

        mockMvc = standaloneSetup(courseRestAdapter).build();
    }

    @Test
    void createCourseStatusCode201() throws Exception {
        Course expected = CourseUtilsTest.getCourse3();

        Course course = Course.builder().name("name").description("description").schedule("schedule").build();
        when(createCourseUseCase.createCourse(course)).thenReturn(expected);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/courses")
                        .content(asJsonString(new CourseCreateRequest("name", "description", "schedule")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void getCourseStatusCode200() throws Exception {
        Course expected = CourseUtilsTest.getCourse1();

        when(getCourseUseCase.getCourseById(1L)).thenReturn(expected);

        mockMvc
                .perform(MockMvcRequestBuilders.get("/v1/courses/1"))
                .andExpect(status().isOk());
    }

    @Test
    void getCoursesStatusCode200() throws Exception {
        List<Course> expected = CourseUtilsTest.getCourses();

        when(getCoursesUseCase.getCourses()).thenReturn(expected);

        mockMvc
                .perform(MockMvcRequestBuilders.get("/v1/courses"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteCoursesStatusCode204() throws Exception {
        doNothing().when(deleteCourseUseCase).deleteCourseById(1L);

        mockMvc
                .perform(MockMvcRequestBuilders.delete("/v1/courses/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateCourseStatusCode200() throws Exception {
        Course expected = CourseUtilsTest.getCourse2();

        Course course = Course.builder().name("name_updated").description("description").schedule("schedule").build();
        when(updateCourseUseCase.updateCourse(course)).thenReturn(expected);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/courses/1")
                        .content(asJsonString(new CourseUpdateRequest(1L, "name_updated", "description", "schedule")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void enrollStudentInCourseUseCaseStatusCode204() throws Exception {
        doNothing().when(enrollStudentInCourseUseCase).enrollStudentInCourse(1L, 2L);

        mockMvc
                .perform(MockMvcRequestBuilders.post("/v1/courses/1/students/2"))
                .andExpect(status().isNoContent());
    }

}
