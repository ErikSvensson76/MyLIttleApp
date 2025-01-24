package org.example.mylittleapp.api;

import org.example.mylittleapp.exception.DuplicatePropertyException;
import org.example.mylittleapp.exception.NoEntityFoundException;
import org.example.mylittleapp.exception.RuntimeIOException;
import org.example.mylittleapp.model.dto.ExceptionResponse;
import org.example.mylittleapp.model.dto.ValidationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class ApplicationExceptionHandler {

  public static final String VALIDATIONS_FAILED = "One or more validations failed";

  private ExceptionResponse getExceptionResponse(
      HttpStatus status,
      Exception ex,
      WebRequest request
  ) {
    ExceptionResponse response = new ExceptionResponse();
    response.setStatus(status.value());
    response.setMessage(ex.getMessage());
    response.setError(status.name());
    response.setPath(request.getDescription(false));
    return response;
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ValidationResponse> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException ex,
      WebRequest request)
  {
    ValidationResponse response = new ValidationResponse();
    response.setMessage(VALIDATIONS_FAILED);
    response.setError(HttpStatus.BAD_REQUEST.name());
    response.setStatus(HttpStatus.BAD_REQUEST.value());
    response.setPath(request.getDescription(false));

    Map<String, List<String>> errors = new HashMap<>();
    List<String> fields = ex.getBindingResult().getFieldErrors().stream()
        .distinct()
        .map(FieldError::getField)
        .toList();

    for(String field : fields){
      List<String> list  = new ArrayList<>();
      for(FieldError error : ex.getFieldErrors(field)){
        list.add(error.getDefaultMessage());
      }
      errors.put(field, list);
    }

    response.setValidationErrors(errors);

    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(DuplicatePropertyException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ExceptionResponse> handleDuplicatePropertyException(
      DuplicatePropertyException ex,
      WebRequest request
  ){
    return new ResponseEntity<>(
        getExceptionResponse(HttpStatus.BAD_REQUEST, ex, request),
        HttpStatus.BAD_REQUEST
    );
  }

  @ExceptionHandler(NoEntityFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<ExceptionResponse> handleNoEntityFoundException(
      NoEntityFoundException ex,
      WebRequest request
  ){
    HttpStatus status = HttpStatus.NOT_FOUND;
    ExceptionResponse response = getExceptionResponse(status, ex, request);
    return new ResponseEntity<>(response, status);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ExceptionResponse> handleIllegalArgumentException(
      IllegalArgumentException ex,
      WebRequest request
  ){
    HttpStatus status = HttpStatus.BAD_REQUEST;
    return ResponseEntity.status(status).body(getExceptionResponse(status, ex, request));
  }

  @ExceptionHandler(RuntimeIOException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ExceptionResponse> handleRuntimeIoException(
      RuntimeIOException ex,
      WebRequest request
  ){
    HttpStatus status = HttpStatus.BAD_REQUEST;
    return ResponseEntity.status(status).body(getExceptionResponse(status, ex, request));
  }

}
