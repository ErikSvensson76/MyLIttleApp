package org.example.mylittleapp.validation.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.example.mylittleapp.service.repos.EntityTopicRepo;
import org.example.mylittleapp.validation.annotations.UniqueTopic;

@RequiredArgsConstructor
public class UniqueTopicValidator implements ConstraintValidator<UniqueTopic, String> {

  private final EntityTopicRepo entityTopicRepo;

  @Override
  public void initialize(UniqueTopic constraintAnnotation) {
    ConstraintValidator.super.initialize(constraintAnnotation);
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if(value == null || value.isEmpty()) return true;
    return entityTopicRepo.findByTopic(value).isEmpty();
  }
}
