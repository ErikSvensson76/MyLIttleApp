package org.example.mylittleapp.model.entity;

import org.example.mylittleapp.TestUtils;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class EntityLessonTest {

  TestUtils testUtils = TestUtils.getInstance();

  @Test
  void entityInstantiation() {
    EntityLesson entityLesson = testUtils.getEntityLesson(true);
    assertNotNull(entityLesson);
    assertNotNull(entityLesson.getId());
    assertNotNull(entityLesson.getLesson());
    assertNotNull(entityLesson.getOrder());
  }

  @Test
  void setSlides() {
    EntityLesson entityLesson = testUtils.getEntityLesson(true);
    List<EntityMarkdown> slides = Stream.generate(() -> testUtils.getEntityMarkdown(true))
        .limit(5)
        .collect(Collectors.toList());

    entityLesson.setSlides(slides);

    assertEquals(5, entityLesson.getSlides().size());
    assertTrue(entityLesson.getSlides().stream()
        .allMatch(entityMarkdown -> entityMarkdown.getLesson().equals(entityLesson))
    );
  }

  @Test
  void removeSlides(){
    EntityLesson entityLesson = testUtils.getEntityLesson(true);
    List<EntityMarkdown> slides = Stream.generate(() -> testUtils.getEntityMarkdown(true))
        .limit(5)
        .collect(Collectors.toList());

    entityLesson.setSlides(slides);
    assertEquals(5, entityLesson.getSlides().size());

    entityLesson.setSlides(null);
    assertTrue(entityLesson.getSlides().isEmpty());
    assertTrue(slides.stream().allMatch(entityMarkdown -> entityMarkdown.getLesson() == null));
  }

  @Test
  void addSlide(){
    EntityLesson entityLesson = testUtils.getEntityLesson(true);
    List<EntityMarkdown> markdowns = Stream.generate(() -> testUtils.getEntityMarkdown(true))
        .limit(2)
        .collect(Collectors.toList());
    markdowns.forEach(entityLesson::addSlide);
    assertEquals(2, entityLesson.getSlides().size());
    assertTrue(entityLesson.getSlides().stream()
        .allMatch(entityMarkdown -> entityMarkdown.getLesson().equals(entityLesson))
    );
  }

  @Test
  void removeSlide(){
    EntityLesson entityLesson = testUtils.getEntityLesson(true);
    List<EntityMarkdown> markdowns = Stream.generate(() -> testUtils.getEntityMarkdown(true))
        .limit(2)
        .collect(Collectors.toList());
    markdowns.forEach(entityLesson::addSlide);
    assertEquals(2, entityLesson.getSlides().size());
    assertTrue(entityLesson.getSlides().stream()
        .allMatch(entityMarkdown -> entityMarkdown.getLesson().equals(entityLesson))
    );
    entityLesson.removeSlide(markdowns.getFirst());
    assertEquals(1, entityLesson.getSlides().size());
    assertTrue(entityLesson.getSlides().stream()
        .allMatch(entityMarkdown -> entityMarkdown.getLesson().equals(entityLesson))
    );
  }

}