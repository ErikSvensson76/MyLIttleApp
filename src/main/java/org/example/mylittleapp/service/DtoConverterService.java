package org.example.mylittleapp.service;

import org.example.mylittleapp.exception.RuntimeIOException;
import org.example.mylittleapp.model.dto.*;
import org.example.mylittleapp.model.entity.*;
import org.springframework.core.io.UrlResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Stream;

@Transactional
public class DtoConverterService {

  public Course entityToCourse(EntityCourse entity){
    Course course = null;
    if(entity != null){
      course = new Course(
          entity.getId(),
          entity.getLanguage(),
          entity.getDifficulty(),
          entity.getTopics().isEmpty() ? new ArrayList<>() : entity.getTopics().stream()
              .filter(Objects::nonNull)
              .map(this::entityToTopic)
              .sorted(Comparator.comparing(Topic::order))
              .toList()
      );
    }
    return course;
  }

  public Topic entityToTopic(EntityTopic entity){
    Topic topic = null;
    if(entity != null){
      topic = new Topic(
          entity.getId(),
          entity.getOrder(),
          entity.getTopic(),
          entity.getDescription(),
          entity.getLessons().isEmpty() ? new ArrayList<>() : entity.getLessons().stream()
              .filter(Objects::nonNull)
              .map(this::entityToLesson)
              .sorted(Comparator.comparing(Lesson::order))
              .toList()
      );
    }
    return topic;
  }

  public Lesson entityToLesson(EntityLesson entity){
    Lesson lesson = null;
    if(entity != null){
      lesson = new Lesson(
          entity.getId(),
          entity.getLesson(),
          entity.getOrder(),
          entity.getSlides().isEmpty() ? new ArrayList<>() : entity.getSlides().stream()
              .filter(Objects::nonNull)
              .map(this::entityToMarkdown)
              .sorted(Comparator.comparing(Markdown::getOrder))
              .toList()
      );
    }
    return lesson;
  }

  public Markdown entityToMarkdown(EntityMarkdown entity, EntityBinary... binaries){
    Markdown markdown = null;
    if(entity != null){
      markdown = new Markdown(
          entity.getId(),
          entity.getOrder(),
          entity.getMarkdownContent()
      );
      if(binaries != null && binaries.length > 0){
        markdown.setUrls(Stream.of(binaries)
            .filter(Objects::nonNull)
            .map(EntityBinary::getUrl)
            .toList());
      }
    }
    return markdown;
  }

  public Binary entityToBinary(EntityBinary entity) {
    Binary binary = null;
    if(entity != null){
      try{
        binary = new Binary(
            entity.getId(),
            entity.getFileName(),
            entity.getMimeType(),
            entity.getUrl(),
            new UrlResource(entity.getUrl())
        );
      }catch (Exception e){
        throw new RuntimeIOException(e.getMessage(), e);
      }
    }
    return binary;
  }

}
