package com.rbailen.sample.coursemanagement.infrastructure.adapter.output.persistence;

import com.rbailen.sample.coursemanagement.application.port.output.CourseOutputPort;
import com.rbailen.sample.coursemanagement.domain.model.Course;
import com.rbailen.sample.coursemanagement.domain.model.Student;
import com.rbailen.sample.coursemanagement.infrastructure.adapter.output.persistence.entity.StudentCourseEntity;
import com.rbailen.sample.coursemanagement.infrastructure.adapter.output.persistence.entity.StudentEntity;
import com.rbailen.sample.coursemanagement.infrastructure.adapter.output.persistence.mapper.CoursePersistenceMapper;
import com.rbailen.sample.coursemanagement.infrastructure.adapter.output.persistence.repository.CourseRepository;
import com.rbailen.sample.coursemanagement.infrastructure.adapter.output.persistence.entity.CourseEntity;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class CoursePersistenceAdapter implements CourseOutputPort {

    private final CourseRepository courseRepository;

    private final CoursePersistenceMapper coursePersistenceMapper;

    @Override
    public Course saveCourse(Course course) {
        CourseEntity courseEntity = coursePersistenceMapper.toCourseEntity(course);
        courseEntity = courseRepository.save(courseEntity);
        return coursePersistenceMapper.toCourse(courseEntity);
    }

    @Override
    public Optional<Course> getCourseById(Long id) {
        Optional<CourseEntity> courseEntityOptional = findCourseById(id);

        if(courseEntityOptional.isEmpty()) {
            return Optional.empty();
        }

        Course course = coursePersistenceMapper.toCourse(courseEntityOptional.get());
        course.setStudents(getStudents(courseEntityOptional.get()));

        return Optional.of(course);
    }

    @Override
    public List<Course> getCourses() {
        List<CourseEntity> courseEntities = courseRepository.findAll();
        return coursePersistenceMapper.toCourseList(courseEntities);
    }

    @Override
    public void deleteCourseById(Long id) {
        courseRepository.deleteById(id);
    }

    @Override
    public Optional<Course> updateCourse(Course course) {
        Optional<CourseEntity> courseEntityOptional = findCourseById(course.getId());

        if(courseEntityOptional.isEmpty()) {
            return Optional.empty();
        }

        CourseEntity courseEntity = courseEntityOptional.get();
        courseEntity.setName(course.getName());
        courseEntity.setDescription(course.getDescription());
        courseEntity.setSchedule(course.getSchedule());

        courseRepository.save(courseEntity);

        course = coursePersistenceMapper.toCourse(courseEntity);
        return Optional.of(course);
    }

    private Optional<CourseEntity> findCourseById(Long id){
        return courseRepository.findById(id);
    }

    private List<Student> getStudents(CourseEntity courseEntity){
        List<Student> students = new ArrayList<>();

        if(null != courseEntity.getStudentCourses() && !courseEntity.getStudentCourses().isEmpty()){
            for(StudentCourseEntity studentCourseEntity: courseEntity.getStudentCourses()){
                StudentEntity studentEntity = studentCourseEntity.getStudentEntity();
                Student student = Student.builder().id(studentEntity.getId()).name(studentEntity.getName()).build();
                students.add(student);
            }
        }

        return students;
    }

}
