package com.rbailen.sample.coursemanagement.infrastructure.adapter.output.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity(name = "student")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    @OneToMany(mappedBy = "studentEntity")
    private List<StudentCourseEntity> studentCourses;

}
