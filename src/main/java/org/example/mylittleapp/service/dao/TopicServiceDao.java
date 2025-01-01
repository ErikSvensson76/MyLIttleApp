package org.example.mylittleapp.service.dao;

import org.example.mylittleapp.exception.DuplicatePropertyException;
import org.example.mylittleapp.exception.NoEntityFoundException;
import org.example.mylittleapp.model.entity.EntityCourse;
import org.example.mylittleapp.model.entity.EntityTopic;
import org.example.mylittleapp.model.input.InputTopic;
import org.example.mylittleapp.service.repos.EntityTopicRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class TopicServiceDao implements TopicService{

  private final EntityTopicRepo repository;
  private final LessonService lessonService;

  public TopicServiceDao(EntityTopicRepo repository, LessonService lessonService) {
    this.repository = repository;
    this.lessonService = lessonService;
  }

  @Override
  public EntityTopic findByTopic(String topic) {
    return repository.findByTopic(topic)
        .orElseThrow(() -> new NoEntityFoundException("No entity found for topic: " + topic));
  }

  @Override
  public EntityTopic findById(String id) {
    return repository.findById(id)
        .orElseThrow(() -> new NoEntityFoundException("No entity found for id: " + id));
  }

  @Override
  public List<EntityTopic> findAll() {
    return repository.findAll();
  }

  @Override
  public EntityTopic save(InputTopic inputTopic) {
    if(inputTopic == null) throw new IllegalArgumentException("Input topic must not be null");
    EntityTopic entityTopic;
    if(inputTopic.getId() == null){
      entityTopic = new EntityTopic();
      entityTopic.setTopic(inputTopic.getTopic().trim());
      entityTopic.setOrder(inputTopic.getOrder());
      entityTopic.setDescription(inputTopic.getDescription().trim());
      entityTopic = repository.save(entityTopic);
    }else{
      entityTopic = findById(inputTopic.getId());
      entityTopic.setDescription(inputTopic.getDescription().trim());
      entityTopic.setOrder(inputTopic.getOrder());
      if(isDuplicateTopic(entityTopic, inputTopic)){
        throw new DuplicatePropertyException("Duplicate topic: " + inputTopic.getTopic());
      }
      entityTopic.setTopic(inputTopic.getTopic().trim());
    }
    if(inputTopic.getLessons() != null && !inputTopic.getLessons().isEmpty()){
        entityTopic.setLessons(
            lessonService.saveAll(inputTopic.getLessons())
        );

    }
    return repository.save(entityTopic);
  }

  public boolean isDuplicateTopic(EntityTopic current, InputTopic input){
    Optional<EntityTopic> optional = repository.findByTopic(input.getTopic());
    if(optional.isPresent()){
      EntityTopic topic = optional.get();
      return topic.getTopic().equals(current.getTopic().trim())
          && !current.getId().equals(topic.getId());
    }
    return false;
  }

  @Override
  public List<EntityTopic> saveAll(List<InputTopic> inputTopics) {
    if(inputTopics == null) throw new IllegalArgumentException("inputTopics must not be null");
    return inputTopics.stream()
        .filter(Objects::nonNull)
        .map(this::save)
        .collect(Collectors.toList());
  }

  @Override
  public boolean delete(String id) {
    EntityTopic entityTopic = findById(id);
    entityTopic.setLessons(null);
    EntityCourse course = entityTopic.getCourse();
    if(course != null && !course.getTopics().isEmpty()){
      course.removeTopic(entityTopic);
    }
    repository.delete(entityTopic);
    return repository.findById(id).isEmpty();
  }

  @Override
  public boolean deleteAll(List<String> ids) {
    if(ids == null) throw new IllegalArgumentException("ids can not be null");
    return ids.stream()
        .filter(Objects::nonNull)
        .map(this::delete)
        .allMatch(bool -> bool.equals(true));
  }
}
