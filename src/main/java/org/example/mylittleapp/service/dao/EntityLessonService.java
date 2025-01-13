package org.example.mylittleapp.service.dao;

import org.example.mylittleapp.model.entity.EntityLesson;
import org.example.mylittleapp.model.input.InputLesson;
import org.example.mylittleapp.service.generic.GenericFetchService;
import org.example.mylittleapp.service.generic.GenericPersistenceService;

public interface EntityLessonService extends
    GenericPersistenceService<InputLesson, EntityLesson>,
    GenericFetchService<EntityLesson>
{
  EntityLesson findByLesson(String lesson);
}
