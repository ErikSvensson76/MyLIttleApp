package org.example.mylittleapp.validation.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.example.mylittleapp.service.repos.EntityLessonRepo;
import org.example.mylittleapp.validation.annotations.UniqueLesson;

@RequiredArgsConstructor
public class UniqueLessonValidator implements ConstraintValidator<UniqueLesson,String> {

  private final EntityLessonRepo entityLessonRepo;

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
