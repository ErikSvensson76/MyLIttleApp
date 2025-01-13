package org.example.mylittleapp.model.dto;

import java.io.Serializable;
import java.util.List;

public record Topic(
    String id,
    Integer order,
    String topic,
    String description,
    List<Lesson> lessons
) implements Serializable {
}
