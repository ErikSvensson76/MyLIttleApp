package org.example.mylittleapp.model.dto;

import java.io.Serializable;
import java.util.List;

public class Markdown implements Serializable {

  private String id;
  private Integer order;
  private String markdownContent;
  private List<String> urls;

  public Markdown(String id, Integer order, String markdownContent) {
    this.id = id;
    this.order = order;
    this.markdownContent = markdownContent;
  }

  public Markdown() {
  }

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

  public String getMarkdownContent() {
    return markdownContent;
  }

  public void setMarkdownContent(String markdownContent) {
    this.markdownContent = markdownContent;
  }

  public List<String> getUrls() {
    return urls;
  }

  public void setUrls(List<String> urls) {
    this.urls = urls;
  }
}
