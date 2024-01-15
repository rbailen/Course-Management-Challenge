package com.rbailen.sample.coursemanagement.infrastructure.adapter.input.rest.data.request;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseUpdateRequest {

    private Long id;

    private String name;

    private String description;

    private String schedule;

}
