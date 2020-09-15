package io.broadcom.assignment.data.model;

import lombok.Value;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

@Value
public class Student implements Serializable {

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
