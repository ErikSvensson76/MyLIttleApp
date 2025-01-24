package org.example.mylittleapp.service.dao;

import org.example.mylittleapp.exception.NoEntityFoundException;
import org.example.mylittleapp.exception.RuntimeIOException;
import org.example.mylittleapp.model.entity.EntityLesson;
import org.example.mylittleapp.model.entity.EntityMarkdown;
import org.example.mylittleapp.model.input.InputMarkdown;
import org.example.mylittleapp.service.repos.EntityMarkdownRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class EntityMarkdownServiceDao implements EntityMarkdownService {

  private final EntityMarkdownRepo repository;

  public EntityMarkdownServiceDao(EntityMarkdownRepo repository) {
    this.repository = repository;
  }

  @Override
  public EntityMarkdown findById(String id) {
    return repository.findById(id)
        .orElseThrow(() -> new NoEntityFoundException("No entity found with id " + id));
  }

  @Override
  public List<EntityMarkdown> findAll() {
    return repository.findAll();
  }

  @Override
  public EntityMarkdown save(InputMarkdown inputMarkdown) {
    if(inputMarkdown == null) throw new IllegalArgumentException("Input markdown cannot be null");

    EntityMarkdown entityMarkdown;
    if(inputMarkdown.getId() == null){
      entityMarkdown = new EntityMarkdown();
    }else{
      entityMarkdown = findById(inputMarkdown.getId());
    }
    entityMarkdown.setOrder(inputMarkdown.getOrder());

    String markdown;
    if(inputMarkdown.getMarkdownFile() == null && inputMarkdown.getMarkdownContent() == null){
      markdown = "";
    }else if(inputMarkdown.getMarkdownFile() != null && !inputMarkdown.getMarkdownFile().isEmpty()){
      MultipartFile file = inputMarkdown.getMarkdownFile();
      if(file.getContentType() == null) throw new IllegalArgumentException("Input markdown file type cannot be null");
      if(!file.getContentType().startsWith("text/markdown")) throw new IllegalArgumentException("Input markdown file type must start with text/markdown");

      try(BufferedReader reader = new BufferedReader(new InputStreamReader(file.getResource().getInputStream()))){
        markdown = reader.lines().collect(Collectors.joining("\n"));
      }catch (Exception e){
        throw new RuntimeIOException("Error reading markdown file", e);
      }
    }else{
      markdown = inputMarkdown.getMarkdownContent().stream()
          .collect(Collectors.joining("\n"));
    }

    entityMarkdown.setMarkdownContent(markdown);

    return repository.save(entityMarkdown);
  }

  @Override
  public List<EntityMarkdown> saveAll(List<InputMarkdown> inputMarkdowns) {
    if(inputMarkdowns == null) throw new IllegalArgumentException("inputMarkdowns cannot be null");
    return inputMarkdowns.stream()
        .filter(Objects::nonNull)
        .map(this::save)
        .collect(Collectors.toList());
  }

  @Override
  public boolean delete(String id) {
    EntityMarkdown entityMarkdown = findById(id);
    EntityLesson entityLesson = entityMarkdown.getLesson();
    if(entityLesson != null && !entityLesson.getSlides().isEmpty()){
      entityLesson.removeSlide(entityMarkdown);
    }
    repository.delete(entityMarkdown);
    return repository.findById(id).isEmpty();
  }

  @Override
  public boolean deleteAll(List<String> ids) {
    if(ids == null) throw new IllegalArgumentException("ids cannot be null");
    return ids.stream()
        .filter(Objects::nonNull)
        .map(this::delete)
        .allMatch(bool -> bool.equals(true));
  }

  @Override
  public List<String> readMultipartFile(MultipartFile file) {
    return List.of();
  }
}
