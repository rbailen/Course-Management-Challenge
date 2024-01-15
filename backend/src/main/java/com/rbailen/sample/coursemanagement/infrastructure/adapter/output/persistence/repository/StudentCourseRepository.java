package com.rbailen.sample.coursemanagement.infrastructure.adapter.output.persistence.repository;

import com.rbailen.sample.coursemanagement.infrastructure.adapter.output.persistence.entity.CourseEntity;
import com.rbailen.sample.coursemanagement.infrastructure.adapter.output.persistence.entity.StudentCourseEntity;
import com.rbailen.sample.coursemanagement.infrastructure.adapter.output.persistence.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentCourseRepository extends JpaRepository<StudentCourseEntity, Long> {

    Optional<StudentCourseEntity> findStudentCourseEntityByStudentEntityAndCourseEntity(StudentEntity studentIdentity, CourseEntity courseEntity);

}
