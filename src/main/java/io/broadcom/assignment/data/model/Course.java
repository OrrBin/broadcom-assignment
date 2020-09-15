package io.broadcom.assignment.data.model;

import lombok.Value;
import org.springframework.data.annotation.Id;

import java.util.List;

@Value
public class Course {
    @Id
    String id;

    String name;
    List<Long> studentIds;
}
