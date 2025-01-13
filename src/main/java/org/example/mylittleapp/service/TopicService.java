package org.example.mylittleapp.service;

import org.example.mylittleapp.model.dto.Topic;
import org.example.mylittleapp.model.input.InputTopic;
import org.example.mylittleapp.service.generic.GenericFetchService;
import org.example.mylittleapp.service.generic.GenericPersistenceService;

public interface TopicService extends
    GenericFetchService<Topic>,
    GenericPersistenceService<InputTopic, Topic> {
  Topic findByTopic(String topic);
}
