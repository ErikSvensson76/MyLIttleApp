package org.example.mylittleapp.service.dao;

import org.example.mylittleapp.model.entity.EntityCourse;
import org.example.mylittleapp.model.input.InputCourse;
import org.example.mylittleapp.service.generic.GenericFetchService;
import org.example.mylittleapp.service.generic.GenericPersistenceService;

public interface CourseService extends
    GenericPersistenceService<InputCourse, EntityCourse>,
    GenericFetchService<EntityCourse>
{
  EntityCourse findByLanguage(String language);
}
