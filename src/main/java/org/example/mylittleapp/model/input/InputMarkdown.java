package org.example.mylittleapp.model.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.example.mylittleapp.validation.OnPut;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.List;

@Validated
public class InputMarkdown implements Serializable {

  @NotBlank(groups = OnPut.class)
  private String id;
  @NotBlank(message = "This field is mandatory.")
  @Positive(message = "This field requires a positive value.")
  private Integer order;
  private List<String> markdownContent;
  private MultipartFile markdownFile;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Integer getOrder() {
    return order;
  }

  public void setOrder(Integer order) {
    this.order = order;
  }

  public List<String> getMarkdownContent() {
    return markdownContent;
  }

  public void setMarkdownContent(List<String> markdownContent) {
    this.markdownContent = markdownContent;
  }

  public MultipartFile getMarkdownFile() {
    return markdownFile;
  }

  public void setMarkdownFile(MultipartFile markdownFile) {
    this.markdownFile = markdownFile;
  }
}
