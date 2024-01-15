package com.rbailen.sample.coursemanagement.infrastructure.adapter.output.persistence;

import com.rbailen.sample.coursemanagement.application.port.output.StudentCourseOutputPort;
import com.rbailen.sample.coursemanagement.domain.exception.CourseNotFound;
import com.rbailen.sample.coursemanagement.domain.exception.StudentEnrolledCourseFound;
import com.rbailen.sample.coursemanagement.infrastructure.adapter.output.persistence.entity.CourseEntity;
import com.rbailen.sample.coursemanagement.infrastructure.adapter.output.persistence.entity.StudentCourseEntity;
import com.rbailen.sample.coursemanagement.infrastructure.adapter.output.persistence.entity.StudentEntity;
import com.rbailen.sample.coursemanagement.infrastructure.adapter.output.persistence.repository.CourseRepository;
import com.rbailen.sample.coursemanagement.infrastructure.adapter.output.persistence.repository.StudentCourseRepository;
import com.rbailen.sample.coursemanagement.infrastructure.adapter.output.persistence.repository.StudentRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class StudentCoursePersistenceAdapter implements StudentCourseOutputPort {

    private final StudentRepository studentRepository;

    private final CourseRepository courseRepository;

    private final StudentCourseRepository studentCourseRepository;

    @Override
    public void enrollStudentInCourse(Long studentId, Long courseId) {
        CourseEntity courseEntity = findCourseById(courseId);
        StudentEntity studentEntity = findStudentById(studentId).get();
        findStudentCourseEntityByStudentEntityAndCourseEntity(studentEntity, courseEntity);

        StudentCourseEntity studentCourseEntity = StudentCourseEntity
                .builder()
                .courseEntity(courseEntity)
                .studentEntity(studentEntity)
                .build();

        studentCourseRepository.save(studentCourseEntity);
    }

    private CourseEntity findCourseById(Long id){
        return courseRepository.findById(id).orElseThrow(() -> new CourseNotFound("Course not found with id " + id));
    }

    private Optional<StudentEntity> findStudentById(Long id){
        return studentRepository.findById(id);
    }

    private void findStudentCourseEntityByStudentEntityAndCourseEntity(StudentEntity studentEntity, CourseEntity courseEntity){
        Optional<StudentCourseEntity> studentCourseEntity =
                studentCourseRepository.findStudentCourseEntityByStudentEntityAndCourseEntity(studentEntity, courseEntity);

        if(studentCourseEntity.isPresent()){
            throw new StudentEnrolledCourseFound("Student " + studentEntity.getId() + " is already enrolled in course " + courseEntity.getId());
        }
    }

}
