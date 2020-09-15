package io.broadcom.assignment.data;

import io.broadcom.assignment.data.model.Course;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "courses", path = "courses")
public interface CourseRepository extends MongoRepository<Course, String> {

//    @Query("{ 'studentIds' : ?0 }")
//    List<Course> findCoursesByAttendingStudent(String studentName);

}
