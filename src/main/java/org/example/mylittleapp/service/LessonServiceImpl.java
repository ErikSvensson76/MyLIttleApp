package org.example.mylittleapp.service;

import org.example.mylittleapp.model.dto.Lesson;
import org.example.mylittleapp.model.input.InputLesson;
import org.example.mylittleapp.service.dao.EntityLessonService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class LessonServiceImpl extends DtoConverterService implements LessonService {

  private final EntityLessonService entityLessonService;

  public LessonServiceImpl(EntityLessonService entityLessonService) {
    this.entityLessonService = entityLessonService;
  }

  @Override
  public Lesson findByLesson(String lesson) {
    return entityToLesson(entityLessonService.findByLesson(lesson));
  }

  @Override
  public Lesson findById(String id) {
    return entityToLesson(entityLessonService.findById(id));
  }

  @Override
  public List<Lesson> findAll() {
    return entityLessonService.findAll().stream()
        .filter(Objects::nonNull)
        .map(this::entityToLesson)
        .sorted(Comparator.comparing(Lesson::order))
        .toList();
  }

  @Override
  public Lesson save(InputLesson inputLesson) {
    return entityToLesson(entityLessonService.save(inputLesson));
  }

  @Override
  public List<Lesson> saveAll(List<InputLesson> inputLessons) {
    return entityLessonService.saveAll(inputLessons).stream()
        .filter(Objects::nonNull)
        .map(this::entityToLesson)
        .sorted(Comparator.comparing(Lesson::order))
        .toList();
  }

  @Override
  public boolean delete(String id) {
    return entityLessonService.delete(id);
  }

  @Override
  public boolean deleteAll(List<String> ids) {
    return entityLessonService.deleteAll(ids);
  }
}
