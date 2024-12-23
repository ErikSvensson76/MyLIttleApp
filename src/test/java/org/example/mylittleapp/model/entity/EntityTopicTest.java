package org.example.mylittleapp.model.entity;

import org.example.mylittleapp.TestUtils;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class EntityTopicTest {

  TestUtils testUtils = TestUtils.getInstance();


  @Test
  void entityInstantiation(){
    EntityTopic topic = testUtils.getEntityTopic(true);
    assertNotNull(topic);
    assertNotNull(topic.getId());
    assertNotNull(topic.getOrder());
    assertNotNull(topic.getTopic());
    assertNotNull(topic.getDescription());
    assertNull(topic.getCourse());
    assertTrue(topic.getLessons().isEmpty());
  }

  @Test
  void setLessons(){
    EntityTopic topic = testUtils.getEntityTopic(true);
    assertNotNull(topic);

    List<EntityLesson> lessons = Stream.generate(() -> testUtils.getEntityLesson(true))
        .limit(5)
        .toList();

    topic.setLessons(lessons);

    assertFalse(topic.getLessons().isEmpty());
    assertEquals(5, topic.getLessons().size());
    assertTrue(
        topic.getLessons().stream().allMatch(entityLesson -> entityLesson.getTopic().equals(topic))
    );
  }

  @Test
  void setLessonsRemove(){
    EntityTopic topic = testUtils.getEntityTopic(true);
    assertNotNull(topic);

    List<EntityLesson> lessons = Stream.generate(() -> testUtils.getEntityLesson(true))
        .limit(5)
        .toList();
    topic.setLessons(lessons);
    assertFalse(topic.getLessons().isEmpty());

    topic.setLessons(null);

    assertTrue(topic.getLessons().isEmpty());
    assertTrue(
        lessons.stream().allMatch(entityLesson -> entityLesson.getTopic() == null)
    );
  }

  @Test
  void addLesson() {
    EntityTopic topic = testUtils.getEntityTopic(true);
    assertNotNull(topic);
    List<EntityLesson> lessons = Stream.generate(() -> testUtils.getEntityLesson(true))
        .limit(2)
        .toList();
    lessons.forEach(topic::addLesson);

    assertEquals(2, topic.getLessons().size());
    assertTrue(topic.getLessons().stream().allMatch(entityLesson -> entityLesson.getTopic().equals(topic)));
  }

  @Test
  void removeLesson(){
    EntityTopic topic = testUtils.getEntityTopic(true);
    assertNotNull(topic);
    List<EntityLesson> lessons = Stream.generate(() -> testUtils.getEntityLesson(true))
        .limit(2)
        .collect(Collectors.toList());
    topic.setLessons(lessons);

    assertEquals(2, topic.getLessons().size());

    topic.removeLesson(lessons.getFirst());

    assertEquals(1, topic.getLessons().size());
  }
}