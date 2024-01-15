package com.rbailen.sample.coursemanagement.infrastructure.adapter.output.persistence.mapper;

import com.rbailen.sample.coursemanagement.domain.model.Student;
import com.rbailen.sample.coursemanagement.infrastructure.adapter.output.persistence.entity.StudentEntity;
import org.mapstruct.Mapper;

@Mapper
public interface StudentPersistenceMapper {

    Student toStudent(StudentEntity studentEntity);

}
