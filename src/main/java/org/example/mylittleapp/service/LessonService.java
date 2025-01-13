package org.example.mylittleapp.service;

import org.example.mylittleapp.model.dto.Lesson;
import org.example.mylittleapp.model.input.InputLesson;
import org.example.mylittleapp.service.generic.GenericFetchService;
import org.example.mylittleapp.service.generic.GenericPersistenceService;

public interface LessonService extends
    GenericFetchService<Lesson>,
    GenericPersistenceService<InputLesson, Lesson> {
  Lesson findByLesson(String lesson);
}
