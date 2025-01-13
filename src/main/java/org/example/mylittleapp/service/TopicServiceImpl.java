package org.example.mylittleapp.service;

import org.example.mylittleapp.model.dto.Topic;
import org.example.mylittleapp.model.input.InputTopic;
import org.example.mylittleapp.service.dao.EntityTopicService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class TopicServiceImpl extends DtoConverterService implements TopicService{

  private final EntityTopicService entityTopicService;

  public TopicServiceImpl(EntityTopicService entityTopicService) {
    this.entityTopicService = entityTopicService;
  }

  @Override
  public Topic findByTopic(String topic) {
    return entityToTopic(entityTopicService.findByTopic(topic));
  }

  @Override
  public Topic findById(String id) {
    return entityToTopic(entityTopicService.findById(id));
  }

  @Override
  public List<Topic> findAll() {
    return entityTopicService.findAll().stream()
        .filter(Objects::nonNull)
        .map(this::entityToTopic)
        .sorted(Comparator.comparing(Topic::order))
        .toList();
  }

  @Override
  public Topic save(InputTopic inputTopic) {
    return entityToTopic(entityTopicService.save(inputTopic));
  }

  @Override
  public List<Topic> saveAll(List<InputTopic> inputTopics) {
    return entityTopicService.saveAll(inputTopics).stream()
        .filter(Objects::nonNull)
        .map(this::entityToTopic)
        .sorted(Comparator.comparing(Topic::order))
        .toList();
  }

  @Override
  public boolean delete(String id) {
    return entityTopicService.delete(id);
  }

  @Override
  public boolean deleteAll(List<String> ids) {
    return entityTopicService.deleteAll(ids);
  }
}
