package org.example.mylittleapp.service;

import org.example.mylittleapp.TestUtils;
import org.example.mylittleapp.model.dto.Course;
import org.example.mylittleapp.model.dto.Lesson;
import org.example.mylittleapp.model.dto.Markdown;
import org.example.mylittleapp.model.dto.Topic;
import org.example.mylittleapp.model.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class DtoConverterServiceTest {

  DtoConverterService underTest = new DtoConverterService();
  TestUtils testUtils = TestUtils.getInstance();

  @Test
  void entityToCourse() {
    EntityCourse entity = testUtils.getEntityCourse(true);
    Course result = underTest.entityToCourse(entity);
    assertNotNull(result);
    assertEquals(entity.getId(), result.id());
    assertEquals(entity.getLanguage(), result.language());
    assertEquals(entity.getDifficulty(), result.difficulty());
    assertTrue(entity.getTopics().isEmpty());
  }

  @Test
  void entityToCourseWithTopics(){
    EntityCourse entity = testUtils.getEntityCourse(true);
    entity.setTopics(Stream.generate(() -> testUtils.getEntityTopic(true))
        .limit(5)
        .collect(Collectors.toList()));

    Course result = underTest.entityToCourse(entity);
    assertEquals(5, result.topics().size());
  }

  @Test
  void entityToCourseWithNull(){
    assertNull(underTest.entityToCourse(null));
  }

  @Test
  void entityToTopic() {
    EntityTopic entity = testUtils.getEntityTopic(true);

    Topic result = underTest.entityToTopic(entity);
    assertNotNull(result);
    assertEquals(entity.getId(), result.id());
    assertEquals(entity.getTopic(), result.topic());
    assertEquals(entity.getOrder(), result.order());
    assertEquals(entity.getDescription(), result.description());
    assertTrue(result.lessons().isEmpty());
  }

  @Test
  void entityToTopicWithLessons(){
    EntityTopic entity = testUtils.getEntityTopic(true);
    entity.setLessons(Stream.generate(() -> testUtils.getEntityLesson(true))
        .limit(5)
        .collect(Collectors.toList()));

    Topic result = underTest.entityToTopic(entity);
    assertEquals(5, result.lessons().size());
  }

  @Test
  void entityToLesson() {
    EntityLesson entity = testUtils.getEntityLesson(true);

    Lesson result = underTest.entityToLesson(entity);
    assertEquals(entity.getId(), result.id());
    assertEquals(entity.getLesson(), result.lesson());
    assertEquals(entity.getOrder(), result.order());
    assertTrue(entity.getSlides().isEmpty());
  }

  @Test
  void entityToLessonWithMarkdown() {
    EntityLesson entity = testUtils.getEntityLesson(true);
    entity.setSlides(Stream.generate(() -> testUtils.getEntityMarkdown(true))
        .limit(5)
        .collect(Collectors.toList()));

    Lesson result = underTest.entityToLesson(entity);
    assertEquals(5, result.slides().size());
  }


  @Test
  void entityToMarkdown() {
    EntityMarkdown entity = testUtils.getEntityMarkdown(true);

    Markdown result = underTest.entityToMarkdown(entity);

    assertNotNull(result);
    assertEquals(entity.getId(), result.getId());
    assertEquals(entity.getOrder(), result.getOrder());
    assertEquals(entity.getMarkdownContent(), result.getMarkdownContent());
    assertNull(result.getUrls());

  }

  @Test
  void entityToMarkdownWithBinary() throws IOException {
    EntityMarkdown entity = testUtils.getEntityMarkdown(true);
    EntityBinary binary = new EntityBinary();
    binary.setId(UUID.randomUUID().toString());
    binary.setUrl("http://localhost:8080/asset/test.jpg");
    binary.setMimeType("image/jpeg");
    binary.setParent(entity);
    binary.setFileName("test.jpg");

    Resource image = new ClassPathResource("test.jpg");
    binary.setBinaryData(image.getContentAsByteArray());

    Markdown result = underTest.entityToMarkdown(entity, binary);
    assertNotNull(result);
    assertEquals(1, result.getUrls().size());
  }
}