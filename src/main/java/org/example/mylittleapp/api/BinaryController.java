package org.example.mylittleapp.api;

import org.example.mylittleapp.model.dto.Binary;
import org.example.mylittleapp.service.BinaryService;
import org.example.mylittleapp.service.MarkdownService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Validated
@RequestMapping("/markdown/asset")
public class BinaryController {

  private final BinaryService binaryService;
  private final MarkdownService markdownService;

  public BinaryController(BinaryService binaryService, MarkdownService markdownService) {
    this.binaryService = binaryService;
    this.markdownService = markdownService;
  }

  @GetMapping("/{filename:.+}")
  public ResponseEntity<byte[]> getBinary(@PathVariable String filename) {
    Binary binary = binaryService.findByFileName(filename);
    try {
      return ResponseEntity.ok()
          .contentType(MediaType.valueOf(binary.mimeType()))
          .body(binary.binaryData().getContentAsByteArray());
    }catch (Exception e){
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping()
  public ResponseEntity<String> saveBinary(
      @RequestParam("parent") String parent,
      @RequestParam("file") MultipartFile file) {
    return ResponseEntity.ok("");
  }





}
