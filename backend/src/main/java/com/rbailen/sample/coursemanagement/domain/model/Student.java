package com.rbailen.sample.coursemanagement.domain.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Student {

    private Long id;

    private String name;

    private String email;

}
