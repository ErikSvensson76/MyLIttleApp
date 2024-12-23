package org.example.mylittleapp.model.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.mylittleapp.validation.OnPut;
import org.example.mylittleapp.validation.annotations.UniqueTopic;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
@Getter
@Setter
@NoArgsConstructor
public class InputTopic {

  @NotBlank(groups = OnPut.class)
  private String id;
  @NotNull(message = "This field is mandatory.")
  @Positive(message = "This field requires a positive value.")
  private Integer order;
  @NotBlank(message = "This field is mandatory.")
  @UniqueTopic
  private String topic;
  @NotBlank(message = "This field is mandatory.")
  @Size(message = "Max 255 characters allowed.", max = 255)
  private String description;
  private List<@NotNull InputLesson> lessons;

}
