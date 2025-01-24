package org.example.mylittleapp.api;

import jakarta.validation.Valid;
import org.example.mylittleapp.model.dto.Lesson;
import org.example.mylittleapp.model.input.InputLesson;
import org.example.mylittleapp.service.LessonService;
import org.example.mylittleapp.validation.OnPost;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/lesson")
public class LessonController {

  private final LessonService lessonService;

  public LessonController(LessonService lessonService) {
    this.lessonService = lessonService;
  }

  @GetMapping()
  public ResponseEntity<List<Lesson>> getAll() {
    return ResponseEntity.ok(lessonService.findAll());
  }

  @PostMapping()
  public ResponseEntity<Lesson> addLesson(@RequestBody @Validated(OnPost.class) @Valid InputLesson lesson) {
    return ResponseEntity.ok(lessonService.save(lesson));
  }

  @GetMapping("/{id}")
  public ResponseEntity<Lesson> findById(@PathVariable String id){
    return ResponseEntity.ok(lessonService.findById(id));
  }

  @PutMapping("/{id}")
  public ResponseEntity<Lesson> updateLesson(
      @PathVariable String id,
      @RequestBody @Validated(OnPost.class) @Valid InputLesson lesson) {
    lesson.setId(id);
    return ResponseEntity.ok(lessonService.save(lesson));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteLesson(@PathVariable String id) {
    return lessonService.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
  }

}
