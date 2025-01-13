package org.example.mylittleapp.model.dto;

import java.io.Serializable;
import java.util.List;

public record Lesson(
    String id,
    String lesson,
    Integer order,
    List<Markdown> slides
) implements Serializable {
}
