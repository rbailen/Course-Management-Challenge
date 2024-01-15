package com.rbailen.sample.coursemanagement.infrastructure.adapter.output.persistence;

import com.rbailen.sample.coursemanagement.application.port.output.StudentOutputPort;
import com.rbailen.sample.coursemanagement.domain.model.Student;
import com.rbailen.sample.coursemanagement.infrastructure.adapter.output.persistence.entity.StudentEntity;
import com.rbailen.sample.coursemanagement.infrastructure.adapter.output.persistence.mapper.StudentPersistenceMapper;
import com.rbailen.sample.coursemanagement.infrastructure.adapter.output.persistence.repository.StudentRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class StudentPersistenceAdapter implements StudentOutputPort {

    private final StudentRepository studentRepository;

    private final StudentPersistenceMapper studentPersistenceMapper;

    @Override
    public Optional<Student> getStudentById(Long id) {
        Optional<StudentEntity> studentEntity = studentRepository.findById(id);

        if(studentEntity.isEmpty()) {
            return Optional.empty();
        }

        Student student = studentPersistenceMapper.toStudent(studentEntity.get());
        return Optional.of(student);
    }

}
