package io.broadcom.assignment.data.model;

import lombok.Data;
import lombok.Value;
import org.springframework.data.annotation.Id;

@Value
public class Grade {
    @Id
    String id;
    int grade;
    long studentId;
    String courseId;


//    @Value
//    public static class GradeIdentifier implements Serializable {
//        long studentId;
//        String courseId;
//
//
//        @Override
//        public boolean equals(Object o) {
//            if (this == o) return true;
//            if (o == null || getClass() != o.getClass()) return false;
//            GradeIdentifier that = (GradeIdentifier) o;
//            return studentId == that.studentId &&
//                    Objects.equals(courseId, that.courseId);
//        }
//
//        @Override
//        public int hashCode() {
//            return Objects.hash(studentId, courseId);
//        }
//    }
}
