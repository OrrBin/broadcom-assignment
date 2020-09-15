package io.broadcom.assignment.data.model;

import lombok.*;
import org.springframework.data.annotation.Id;

@Value
public class Student {
    @Id
    long id;

    String firstName;
    String lastName;
    String email;

    @Override
    public String toString() {
        return String.format(
                "Student[id=%s, firstName='%s', lastName='%s', email='%s']",
                id, firstName, lastName, email);
    }
}
