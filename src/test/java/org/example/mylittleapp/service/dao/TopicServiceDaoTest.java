package org.example.mylittleapp.service.dao;

import org.example.mylittleapp.TestUtils;
import org.example.mylittleapp.model.entity.EntityTopic;
import org.example.mylittleapp.model.input.InputLesson;
import org.example.mylittleapp.model.input.InputTopic;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@Transactional
@DirtiesContext
class TopicServiceDaoTest {

  @Autowired private TopicServiceDao underTest;
  @Autowired private TestEntityManager em;
  private final TestUtils testUtils = TestUtils.getInstance();

  @Test
  void saveSimpleTopic() {
    InputTopic input = testUtils.getInputTopic();

    EntityTopic result = underTest.save(input);

    assertNotNull(result.getId());
    assertEquals(input.getTopic(), result.getTopic());
    assertEquals(input.getDescription(), result.getDescription());
    assertEquals(input.getOrder(), result.getOrder());
    assertNull(result.getCourse());
    assertTrue(result.getLessons().isEmpty());
  }

  @Test
  void updateSimpleTopic() {
    EntityTopic topic = em.persist(testUtils.getEntityTopic(false));
    InputTopic input = new InputTopic();
    input.setId(topic.getId());
    String arrays = "Arrays";
    input.setTopic(arrays);
    String description = "Some text about arrays";
    input.setDescription(description);
    input.setOrder(topic.getOrder());

    EntityTopic result = underTest.save(input);
    assertNotNull(result);
    assertEquals(input.getId(), result.getId());
    assertEquals(arrays, result.getTopic());
    assertEquals(description, result.getDescription());
    assertEquals(topic.getOrder(), result.getOrder());
    assertTrue(result.getLessons().isEmpty());
    assertNull(result.getCourse());
  }

  @Test
  void saveTopicWithNestedLessons() {
    InputTopic input = testUtils.getInputTopic();
    input.setLessons(Stream.generate(testUtils::getInputLesson)
        .limit(3)
        .collect(Collectors.toList())
    );
    EntityTopic result = underTest.save(input);
    assertNotNull(result);
    assertNotNull(result.getId());
    assertEquals(input.getTopic(), result.getTopic());
    assertEquals(input.getDescription(), result.getDescription());
    assertEquals(input.getOrder(), result.getOrder());
    assertNull(result.getCourse());
    assertEquals(3, result.getLessons().size());
    assertTrue(result.getLessons().stream().allMatch(l -> l.getTopic().equals(result)));
  }

  @Test
  void updateTopicWithNestedLessons() {
    InputTopic input = testUtils.getInputTopic();
    input.setLessons(Stream.generate(testUtils::getInputLesson)
        .limit(3)
        .collect(Collectors.toList())
    );

    EntityTopic topic = underTest.save(input);
    assertNotNull(topic);

    InputTopic test = new InputTopic();
    test.setId(test.getId());
    test.setTopic(topic.getTopic());
    test.setDescription(topic.getDescription());
    test.setOrder(topic.getOrder());
    List<InputLesson> lessons = topic.getLessons().stream()
        .map(lesson -> {
          InputLesson obj = new InputLesson();
          obj.setId(lesson.getId());
          obj.setLesson(lesson.getLesson());
          obj.setOrder(lesson.getOrder());
          return obj;
        }).collect(Collectors.toList());
    test.setLessons(lessons);

    assertTrue(lessons.remove(lessons.getLast()));

    EntityTopic result = underTest.save(test);
    assertNotNull(result);
    assertEquals(2, result.getLessons().size());
  }

  @Test
  void deleteTopic() {
    EntityTopic topic = em.persist(testUtils.getEntityTopic(false));
    assertNotNull(topic.getId());
    String id = topic.getId();

    assertTrue(underTest.delete(id));
  }

  @Test
  void deleteTopicWithNestedLessons() {
    EntityTopic topic = em.persist(testUtils.getEntityTopic(false));
    topic.setLessons(Stream.generate(() -> testUtils.getEntityLesson(false))
        .limit(5)
        .map(em::persist)
        .collect(Collectors.toList())
    );
    assertNotNull(topic);
    assertFalse(topic.getLessons().isEmpty());

    assertTrue(underTest.delete(topic.getId()));
  }
}