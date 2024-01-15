package com.rbailen.sample.coursemanagement.infrastructure.adapter.output.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity(name = "course")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private String schedule;

    @OneToMany(mappedBy = "courseEntity", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<StudentCourseEntity> studentCourses;

}
