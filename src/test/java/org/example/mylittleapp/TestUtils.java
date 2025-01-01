package org.example.mylittleapp;

import net.datafaker.Faker;
import org.example.mylittleapp.model.entity.EntityCourse;
import org.example.mylittleapp.model.entity.EntityLesson;
import org.example.mylittleapp.model.entity.EntityMarkdown;
import org.example.mylittleapp.model.entity.EntityTopic;
import org.example.mylittleapp.model.input.InputCourse;
import org.example.mylittleapp.model.input.InputLesson;
import org.example.mylittleapp.model.input.InputMarkdown;
import org.example.mylittleapp.model.input.InputTopic;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestUtils {

  private static final TestUtils INSTANCE = new TestUtils();

  public static TestUtils getInstance() {
    return INSTANCE;
  }

  private final Faker faker = new Faker();

  private TestUtils() {}

  public EntityCourse getEntityCourse(boolean generateId) {
    EntityCourse course = new EntityCourse();
    if(generateId) {
      course.setId(UUID.randomUUID().toString());
    }
    course.setDifficulty(faker.lorem().characters(1,5));
    course.setLanguage(faker.programmingLanguage().name());
    return course;
  }

  public InputCourse getInputCourse() {
    InputCourse course = new InputCourse();
    course.setDifficulty(faker.lorem().characters(10,50));
    course.setLanguage(faker.lorem().characters(10,100));
    return course;
  }

  public EntityTopic getEntityTopic(boolean generateId) {
    EntityTopic topic = new EntityTopic();
    if(generateId) {
      topic.setId(UUID.randomUUID().toString());
    }
    topic.setOrder(faker.number().numberBetween(1,100));
    topic.setTopic(faker.lorem().characters(1,5));
    topic.setDescription(faker.lorem().characters(20,255));
    return topic;
  }

  public InputTopic getInputTopic() {
    InputTopic topic = new InputTopic();
    topic.setOrder(faker.number().numberBetween(1,100));
    topic.setTopic(faker.lorem().characters(10,100));
    topic.setDescription(faker.lorem().characters(20,255));
    return topic;
  }

  public EntityLesson getEntityLesson(boolean generateId) {
    EntityLesson lesson = new EntityLesson();
    if(generateId) {
      lesson.setId(UUID.randomUUID().toString());
    }
    lesson.setOrder(faker.number().numberBetween(1,100));
    lesson.setLesson(faker.lorem().characters(10, 20));
    return lesson;
  }

  public InputLesson getInputLesson() {
    InputLesson lesson = new InputLesson();
    lesson.setOrder(faker.number().numberBetween(1,100));
    lesson.setLesson(faker.lorem().characters(10, 100));
    return lesson;
  }

  public EntityMarkdown getEntityMarkdown(boolean generateId) {
    EntityMarkdown markdown = new EntityMarkdown();
    if(generateId) {
      markdown.setId(UUID.randomUUID().toString());
    }
    markdown.setMarkdownContent("# "+faker.lorem().characters(5));
    markdown.setOrder(faker.number().numberBetween(1,20));
    return markdown;
  }

  public InputMarkdown getInputMarkdown() {
    InputMarkdown markdown = new InputMarkdown();
    markdown.setOrder(faker.number().numberBetween(1,100));
    List<String> markdownContent = Stream.generate(
        () -> "# " + faker.lorem().characters(5,10))
        .limit(5)
        .collect(Collectors.toList());
    markdown.setMarkdownContent(markdownContent);
    return markdown;
  }

}
