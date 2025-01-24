package org.example.mylittleapp.model.dto;

import java.util.List;
import java.util.Map;

public class ValidationResponse extends ExceptionResponse{

  private Map<String, List<String>> validationErrors;

  public ValidationResponse() {}

  public Map<String, List<String>> getValidationErrors() {
    return validationErrors;
  }

  public void setValidationErrors(Map<String, List<String>> validationErrors) {
    this.validationErrors = validationErrors;
  }

}
