package com.rbailen.sample.coursemanagement.domain.event;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentEnrolledEvent {

    private Long idStudent;

    private Long idCourse;

    private String email;

    private LocalDateTime date;

    public StudentEnrolledEvent(Long idStudent, Long idCourse, String email) {
        this.idStudent = idStudent;
        this.idCourse = idCourse;
        this.email = email;
        this.date = LocalDateTime.now();
    }

}

