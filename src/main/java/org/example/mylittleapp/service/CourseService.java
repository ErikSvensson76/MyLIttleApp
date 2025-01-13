package org.example.mylittleapp.service;

import org.example.mylittleapp.model.dto.Course;
import org.example.mylittleapp.model.input.InputCourse;
import org.example.mylittleapp.service.generic.GenericFetchService;
import org.example.mylittleapp.service.generic.GenericPersistenceService;

public interface CourseService extends
    GenericFetchService<Course>,
    GenericPersistenceService<InputCourse, Course> {
  Course findByLanguage(String language);
}
