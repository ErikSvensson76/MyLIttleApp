package org.example.mylittleapp.model.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.mylittleapp.validation.OnPut;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
@Getter
@Setter
@AllArgsConstructor
public class InputMarkdown {

  @NotBlank(groups = OnPut.class)
  private String id;
  @NotBlank(message = "This field is mandatory.")
  @Positive(message = "This field requires a positive value.")
  private Integer order;
  private List<String> markdownContent;

}
