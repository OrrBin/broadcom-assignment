package io.broadcom.assignment.data.model;

import lombok.Value;
import org.springframework.data.annotation.Id;

@Value
public class Grade {

    /**
     * NOTE: Didnt have time to change this entity to have  studentId,courseId as ID, so we can have duplicates
     * In the sense of two entries in the DB for the same user in the same course.
     * This is not COOL at all , if i had more time i would definitely fix that.
     */
    @Id
    String id;
    int grade;
    long studentId;
    String courseId;
}
