package org.example.mylittleapp.service;

import org.example.mylittleapp.model.dto.Course;
import org.example.mylittleapp.model.input.InputCourse;
import org.example.mylittleapp.service.dao.EntityCourseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class CourseServiceImpl extends DtoConverterService implements CourseService {

  private final EntityCourseService entityCourseService;

  public CourseServiceImpl(EntityCourseService entityCourseService) {
    this.entityCourseService = entityCourseService;
  }

  @Override
  public Course findByLanguage(String language) {
    return entityToCourse(entityCourseService.findByLanguage(language));
  }

  @Override
  public Course findById(String id) {
    return entityToCourse(entityCourseService.findById(id));
  }

  @Override
  public List<Course> findAll() {
    return entityCourseService.findAll().stream()
        .filter(Objects::nonNull)
        .map(this::entityToCourse)
        .toList();
  }

  @Override
  public Course save(InputCourse inputCourse) {
    return entityToCourse(entityCourseService.save(inputCourse));
  }

  @Override
  public List<Course> saveAll(List<InputCourse> inputCourses) {
    return entityCourseService.saveAll(inputCourses).stream()
        .filter(Objects::nonNull)
        .map(this::entityToCourse)
        .toList();
  }

  @Override
  public boolean delete(String id) {
    return entityCourseService.delete(id);
  }

  @Override
  public boolean deleteAll(List<String> ids) {
    return entityCourseService.deleteAll(ids);
  }

}
