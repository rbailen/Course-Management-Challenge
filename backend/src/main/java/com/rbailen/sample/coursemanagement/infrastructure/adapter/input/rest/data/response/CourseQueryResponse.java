package com.rbailen.sample.coursemanagement.infrastructure.adapter.input.rest.data.response;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseQueryResponse {

    private Long id;

    private String name;

    private String description;

    private String schedule;

    private List<StudentQueryResponse> students;

}
