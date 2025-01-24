package org.example.mylittleapp.api;

import jakarta.validation.Valid;
import org.example.mylittleapp.model.dto.Topic;
import org.example.mylittleapp.model.input.InputTopic;
import org.example.mylittleapp.service.TopicService;
import org.example.mylittleapp.validation.OnPost;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/topic")
public class TopicController {

  private final TopicService topicService;

  public TopicController(TopicService topicService) {
    this.topicService = topicService;
  }

  @GetMapping()
  public ResponseEntity<List<Topic>> getTopics() {
    return ResponseEntity.ok(topicService.findAll());
  }

  @PostMapping()
  public ResponseEntity<Topic> addTopic(@RequestBody @Validated(OnPost.class) @Valid InputTopic topic) {
    return ResponseEntity.ok(topicService.save(topic));
  }

  @GetMapping("/{id}")
  public ResponseEntity<Topic> findById(@PathVariable String id){
    return ResponseEntity.ok(topicService.findById(id));
  }

  @PutMapping("/{id}")
  public ResponseEntity<Topic> updateTopic(
      @PathVariable String id,
      @RequestBody @Validated(OnPost.class) @Valid InputTopic topic) {
    topic.setId(id);
    return ResponseEntity.ok(topicService.save(topic));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTopic(@PathVariable String id) {
    return topicService.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
  }

}
