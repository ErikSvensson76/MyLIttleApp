package org.example.mylittleapp.model.dto;

import java.io.Serializable;
import java.util.List;

public record Course(
    String id,
    String language,
    String difficulty,
    List<Topic> topics
) implements Serializable {
}
