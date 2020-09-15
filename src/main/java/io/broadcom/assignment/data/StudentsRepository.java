package io.broadcom.assignment.data;

import io.broadcom.assignment.data.model.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "students", path = "students")
public interface StudentsRepository extends MongoRepository<Student, Long> {

    Student findByFirstName(String firstName);
    List<Student> findByLastName(String lastName);
}
