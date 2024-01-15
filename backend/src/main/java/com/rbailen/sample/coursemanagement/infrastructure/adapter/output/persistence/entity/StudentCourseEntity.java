package com.rbailen.sample.coursemanagement.infrastructure.adapter.output.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "student_course")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentCourseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private StudentEntity studentEntity;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private CourseEntity courseEntity;

}
