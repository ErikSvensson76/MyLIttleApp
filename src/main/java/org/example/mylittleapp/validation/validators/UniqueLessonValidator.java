package org.example.mylittleapp.validation.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.mylittleapp.service.repos.EntityLessonRepo;
import org.example.mylittleapp.validation.annotations.UniqueLesson;

public class UniqueLessonValidator implements ConstraintValidator<UniqueLesson,String> {

  private final EntityLessonRepo entityLessonRepo;

  public UniqueLessonValidator(EntityLessonRepo entityLessonRepo) {
    this.entityLessonRepo = entityLessonRepo;
  }

  @Override
  public void initialize(UniqueLesson constraintAnnotation) {
    ConstraintValidator.super.initialize(constraintAnnotation);
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if(value == null || value.isEmpty()) return true;
    return entityLessonRepo.findByLesson(value).isEmpty();
  }
}
