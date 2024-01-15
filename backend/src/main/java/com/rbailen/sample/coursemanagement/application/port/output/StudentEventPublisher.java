package com.rbailen.sample.coursemanagement.application.port.output;

import com.rbailen.sample.coursemanagement.domain.event.StudentEnrolledEvent;

public interface StudentEventPublisher {

    void publishStudentEnrolledEvent(StudentEnrolledEvent event);

}
