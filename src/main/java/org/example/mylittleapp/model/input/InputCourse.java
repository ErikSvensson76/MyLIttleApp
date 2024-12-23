package org.example.mylittleapp.model.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.mylittleapp.validation.OnPut;
import org.example.mylittleapp.validation.annotations.UniqueLanguage;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
@Getter
@Setter
@NoArgsConstructor
public class InputCourse {

  @NotBlank(groups = OnPut.class)
  private String id;
  @NotBlank(message = "This field is mandatory")
  @UniqueLanguage
  private String language;
  @NotBlank(message = "This field is mandatory")
  private String difficulty;
  private List<@NotNull InputTopic> topics;

}


