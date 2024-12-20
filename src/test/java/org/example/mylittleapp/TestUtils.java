package org.example.mylittleapp;

import net.datafaker.Faker;
import org.example.mylittleapp.model.entity.EntityCourse;
import org.example.mylittleapp.model.entity.EntityLesson;
import org.example.mylittleapp.model.entity.EntityMarkdown;
import org.example.mylittleapp.model.entity.EntityTopic;

import java.util.UUID;

public class TestUtils {

  private static final TestUtils INSTANCE = new TestUtils();

  public static TestUtils getInstance() {
    return INSTANCE;
  }

  private final Faker faker = new Faker();

  private TestUtils() {}

  public EntityCourse getRandomCourse(boolean generateId) {
    EntityCourse course = new EntityCourse();
    if(generateId) {
      course.setId(UUID.randomUUID().toString());
    }
    course.setDifficulty(faker.lorem().characters(1,5));
    course.setLanguage(faker.programmingLanguage().name());
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

  public EntityLesson getEntityLesson(boolean generateId) {
    EntityLesson lesson = new EntityLesson();
    if(generateId) {
      lesson.setId(UUID.randomUUID().toString());
    }
    lesson.setOrder(faker.number().numberBetween(1,100));
    lesson.setLesson(faker.lorem().characters(10, 20));
    return lesson;
  }

  public EntityMarkdown getEntityMarkdown(boolean generateId) {
    EntityMarkdown markdown = new EntityMarkdown();
    if(generateId) {
      markdown.setId(UUID.randomUUID().toString());
    }
    markdown.setMarkdownContent("#"+faker.lorem().characters(5));
    markdown.setOrder(faker.number().numberBetween(1,20));
    return markdown;
  }

}
