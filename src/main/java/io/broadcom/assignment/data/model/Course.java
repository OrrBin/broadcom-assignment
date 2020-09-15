package io.broadcom.assignment.data.model;

import lombok.Value;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.List;

@Value
public class Course implements Serializable {
    @Id
    String id;

    String name;
    List<Long> studentIds;
}
