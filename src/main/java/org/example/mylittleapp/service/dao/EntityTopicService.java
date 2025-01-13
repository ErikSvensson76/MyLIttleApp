package org.example.mylittleapp.service.dao;

import org.example.mylittleapp.model.entity.EntityTopic;
import org.example.mylittleapp.model.input.InputTopic;
import org.example.mylittleapp.service.generic.GenericFetchService;
import org.example.mylittleapp.service.generic.GenericPersistenceService;

public interface EntityTopicService extends
    GenericPersistenceService<InputTopic, EntityTopic>,
    GenericFetchService<EntityTopic>
{
  EntityTopic findByTopic(String topic);
}
