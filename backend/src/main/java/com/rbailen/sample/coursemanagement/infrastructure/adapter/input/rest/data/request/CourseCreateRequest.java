package com.rbailen.sample.coursemanagement.infrastructure.adapter.input.rest.data.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseCreateRequest {

    @NotEmpty(message = "Name may not be empty")
    private String name;

    @NotEmpty(message = "Description may not be empty")
    private String description;

    @NotEmpty(message = "Schedule may not be empty")
    private String schedule;

}
