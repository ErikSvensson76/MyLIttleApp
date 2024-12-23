package org.example.mylittleapp.validation.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.mylittleapp.service.repos.EntityCourseRepo;
import org.example.mylittleapp.validation.annotations.UniqueLanguage;

public class UniqueLanguageValidator implements ConstraintValidator<UniqueLanguage, String> {
  @Override
  public void initialize(UniqueLanguage constraintAnnotation) {
    ConstraintValidator.super.initialize(constraintAnnotation);
  }

  private final EntityCourseRepo entityCourseRepo;

  public UniqueLanguageValidator(EntityCourseRepo entityCourseRepo) {
    this.entityCourseRepo = entityCourseRepo;
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return value == null || entityCourseRepo.findByLanguage(value).isEmpty();
  }
}
