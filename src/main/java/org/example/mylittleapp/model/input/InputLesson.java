package org.example.mylittleapp.model.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.mylittleapp.validation.OnPut;
import org.example.mylittleapp.validation.annotations.UniqueLesson;
import org.springframework.validation.annotation.Validated;

@Validated
@Getter
@Setter
@NoArgsConstructor
public class InputLesson {

  @NotBlank(groups = OnPut.class)
  private String id;
  @NotBlank(message = "This field is mandatory.")
  @UniqueLesson
  private String lesson;
  @NotNull(message = "This field is mandatory.")
  @Positive(message = "This field requires a positive value.")
  private Integer order;
  //private List<@NotNull InputMarkdown> slides;


}
