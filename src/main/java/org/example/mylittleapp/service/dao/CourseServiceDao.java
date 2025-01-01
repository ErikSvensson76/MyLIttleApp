package org.example.mylittleapp.service.dao;

import org.example.mylittleapp.exception.DuplicatePropertyException;
import org.example.mylittleapp.exception.NoEntityFoundException;
import org.example.mylittleapp.model.entity.EntityCourse;
import org.example.mylittleapp.model.input.InputCourse;
import org.example.mylittleapp.service.repos.EntityCourseRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class CourseServiceDao implements CourseService{

  private final EntityCourseRepo repository;
  private final TopicService topicService;

  public CourseServiceDao(EntityCourseRepo repository, TopicService topicService) {
    this.repository = repository;
    this.topicService = topicService;
  }

  @Override
  public EntityCourse findByLanguage(String language) {
    return repository.findByLanguage(language)
        .orElseThrow(() -> new NoEntityFoundException("No entity course found with language: " + language));
  }

  @Override
  public EntityCourse findById(String id) {
    return repository.findById(id)
        .orElseThrow(() -> new NoEntityFoundException("No entity found with id: " + id));
  }

  @Override
  public List<EntityCourse> findAll() {
    return repository.findAll();
  }

  @Override
  public EntityCourse save(InputCourse inputCourse) {
    if(inputCourse == null) throw new IllegalArgumentException("Input course cannot be null");

    EntityCourse entityCourse;
    if(inputCourse.getId() == null){
      entityCourse = new EntityCourse();
      entityCourse.setLanguage(inputCourse.getLanguage().trim());
      entityCourse.setDifficulty(inputCourse.getDifficulty());
      entityCourse = repository.save(entityCourse);
    }else {
      entityCourse = findById(inputCourse.getId());
      entityCourse.setDifficulty(inputCourse.getDifficulty().trim());
      if(isDuplicateLanguage(entityCourse, inputCourse)){
        throw new DuplicatePropertyException("Duplicate language: " + inputCourse.getLanguage());
      }
      entityCourse.setLanguage(inputCourse.getLanguage().trim());
    }
    if(inputCourse.getTopics() != null && !inputCourse.getTopics().isEmpty()){
      entityCourse.setTopics(
          topicService.saveAll(inputCourse.getTopics())
      );
    }
    return repository.save(entityCourse);
  }

  public boolean isDuplicateLanguage(EntityCourse current, InputCourse inputCourse) {
    Optional<EntityCourse> optional = repository.findByLanguage(inputCourse.getLanguage());
    if(optional.isPresent()){
      EntityCourse course = optional.get();
      return course.getLanguage().equals(inputCourse.getLanguage().trim())
          && !current.getId().equals(course.getId());
    }
    return false;
  }

  @Override
  public List<EntityCourse> saveAll(List<InputCourse> inputCourses) {
    if(inputCourses == null) throw new IllegalArgumentException("inputCourses cannot be null");
    return inputCourses.stream()
        .filter(Objects::nonNull)
        .map(this::save)
        .collect(Collectors.toList());
  }

  @Override
  public boolean delete(String id) {
    EntityCourse entityCourse = findById(id);
    entityCourse.setTopics(null);
    repository.delete(entityCourse);
    return repository.findById(id).isEmpty();
  }

  @Override
  public boolean deleteAll(List<String> ids) {
    if(ids == null) throw new IllegalArgumentException("ids cannot be null");
    return ids.stream()
        .filter(Objects::nonNull)
        .map(this::delete)
        .allMatch(bool -> bool.equals(true));
  }
}
