package io.broadcom.assignment;

import io.broadcom.assignment.data.model.Course;
import io.broadcom.assignment.data.model.Grade;
import io.broadcom.assignment.data.model.Student;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.stereotype.Component;

@Component
public class ExposeEntityIdRestMvcConfiguration implements RepositoryRestConfigurer {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(Student.class);
        config.exposeIdsFor(Course.class);
        config.exposeIdsFor(Grade.class);
    }
}