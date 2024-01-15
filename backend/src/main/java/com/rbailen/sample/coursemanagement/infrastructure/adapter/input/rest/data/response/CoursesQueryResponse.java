package com.rbailen.sample.coursemanagement.infrastructure.adapter.input.rest.data.response;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CoursesQueryResponse {

    private Long id;

    private String name;

    private String description;

    private String schedule;

}
