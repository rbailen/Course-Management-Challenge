package com.rbailen.sample.coursemanagement.domain.model;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Course {

    private Long id;

    private String name;

    private String description;

    private String schedule;

    private List<Student> students;

}
