package com.rbailen.sample.coursemanagement.infrastructure.adapter.input.rest.data.response;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentQueryResponse {

    private Long id;

    private String name;

}
