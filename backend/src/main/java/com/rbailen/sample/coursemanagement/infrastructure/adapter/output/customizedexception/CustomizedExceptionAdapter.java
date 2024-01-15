package com.rbailen.sample.coursemanagement.infrastructure.adapter.output.customizedexception;

import com.rbailen.sample.coursemanagement.domain.exception.CourseNotFound;
import com.rbailen.sample.coursemanagement.domain.exception.StudentEnrolledCourseFound;
import com.rbailen.sample.coursemanagement.domain.exception.StudentNotFound;
import io.micrometer.tracing.Tracer;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@AllArgsConstructor
public class CustomizedExceptionAdapter extends ResponseEntityExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(CustomizedExceptionAdapter.class);

    private final Tracer tracer;
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex) {
        String traceId = tracer.currentSpan().context().traceId();
        LOG.error("An internal error has occurred in the request with traceId {}: {}", traceId, ex.getMessage());

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());

        problemDetail.setType(URI.create("https://api.courses.com/errors/internal-server-error"));

        Map<String, Object> properties = new HashMap<>();
        properties.put("date", LocalDateTime.now());
        problemDetail.setProperties(properties);

        return new ResponseEntity(problemDetail, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CourseNotFound.class)
    public final ResponseEntity<Object> handleCourseNotFoundException(CourseNotFound ex) {
        return getNotFoundObjectResponseEntity(ex.getMessage());
    }

    @ExceptionHandler(StudentNotFound.class)
    public final ResponseEntity<Object> handleStudentNotFoundException(StudentNotFound ex) {
        return getNotFoundObjectResponseEntity(ex.getMessage());
    }

    @ExceptionHandler(StudentEnrolledCourseFound.class)
    public final ResponseEntity<Object> handleStudentEnrolledCourseFoundException(StudentEnrolledCourseFound ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());

        problemDetail.setType(URI.create("https://api.courses.com/errors/conflict"));

        Map<String, Object> properties = new HashMap<>();
        properties.put("date", LocalDateTime.now());
        problemDetail.setProperties(properties);

        return new ResponseEntity(problemDetail, HttpStatus.CONFLICT);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String errorMessage = ex.getBindingResult().getAllErrors().stream().findFirst().map(ObjectError::getDefaultMessage).orElse(null);

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, errorMessage);

        problemDetail.setType(URI.create("https://api.courses.com/errors/bad-request"));

        Map<String, Object> properties = new HashMap<>();
        properties.put("date", LocalDateTime.now());
        problemDetail.setProperties(properties);

        return new ResponseEntity(problemDetail, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Object> getNotFoundObjectResponseEntity(String message) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, message);

        problemDetail.setType(URI.create("https://api.courses.com/errors/not-found"));

        Map<String, Object> properties = new HashMap<>();
        properties.put("date", LocalDateTime.now());
        problemDetail.setProperties(properties);

        return new ResponseEntity(problemDetail, HttpStatus.NOT_FOUND);
    }

}