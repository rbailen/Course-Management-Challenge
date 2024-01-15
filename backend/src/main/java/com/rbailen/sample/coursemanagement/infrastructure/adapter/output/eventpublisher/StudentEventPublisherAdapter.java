package com.rbailen.sample.coursemanagement.infrastructure.adapter.output.eventpublisher;

import com.rbailen.sample.coursemanagement.application.port.output.StudentEventPublisher;
import com.rbailen.sample.coursemanagement.domain.event.StudentEnrolledEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;

@RequiredArgsConstructor
public class StudentEventPublisherAdapter implements StudentEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void publishStudentEnrolledEvent(StudentEnrolledEvent event) {
        applicationEventPublisher.publishEvent(event);
    }

}
