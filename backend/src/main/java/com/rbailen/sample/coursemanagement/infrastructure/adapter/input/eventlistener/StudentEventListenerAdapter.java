package com.rbailen.sample.coursemanagement.infrastructure.adapter.input.eventlistener;

import com.rbailen.sample.coursemanagement.domain.event.StudentEnrolledEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class StudentEventListenerAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(StudentEventListenerAdapter.class);

    @EventListener
    public void handle(StudentEnrolledEvent event){
        LOG.info("Sending an email to student with id {} and email {} indicating thay they have enrolled in course with id {} at {}",
                event.getIdStudent(), event.getEmail(), event.getIdCourse(), event.getDate());
    }

}
