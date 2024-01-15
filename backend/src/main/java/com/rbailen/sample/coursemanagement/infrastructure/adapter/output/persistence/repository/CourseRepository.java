package com.rbailen.sample.coursemanagement.infrastructure.adapter.output.persistence.repository;

import com.rbailen.sample.coursemanagement.infrastructure.adapter.output.persistence.entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, Long> {

}
