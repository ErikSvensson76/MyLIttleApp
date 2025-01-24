package org.example.mylittleapp.api;

import jakarta.validation.Valid;
import org.example.mylittleapp.model.dto.Markdown;
import org.example.mylittleapp.model.input.InputMarkdown;
import org.example.mylittleapp.service.MarkdownService;
import org.example.mylittleapp.validation.OnPost;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/markdown")
public class MarkdownController {

  private final MarkdownService markdownService;



  public MarkdownController(MarkdownService markdownService) {
    this.markdownService = markdownService;
  }

  @GetMapping()
  public ResponseEntity<List<Markdown>> getMarkdown() {
    return ResponseEntity.ok(markdownService.findAll());
  }

  @PostMapping()
  public ResponseEntity<Markdown> createMarkdown(@RequestBody @Validated(OnPost.class) @Valid InputMarkdown markdown) {
    return ResponseEntity.ok(markdownService.save(markdown));
  }

  @GetMapping("/{id}")
  public ResponseEntity<Markdown> getMarkdownById(@PathVariable String id) {
    return ResponseEntity.ok(markdownService.findById(id));
  }

  @PutMapping("/{id}")
  public ResponseEntity<Markdown> updateMarkdown(
      @PathVariable String id,
      @RequestBody @Validated(OnPost.class) @Valid InputMarkdown markdown) {
    markdown.setId(id);
    return ResponseEntity.ok(markdownService.save(markdown));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteMarkdown(@PathVariable String id) {
    return markdownService.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
  }
  
}
