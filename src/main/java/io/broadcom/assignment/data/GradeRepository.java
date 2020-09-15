package io.broadcom.assignment.data;

import io.broadcom.assignment.data.model.Grade;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "grades", path = "grades")
public interface GradeRepository extends MongoRepository<Grade, String> {

    List<Grade> findByStudentId(long studentId);

    List<Grade> findByCourseId(String courseId);

    Grade findFirstByStudentIdAndCourseId(long studentId, String courseId);

}
