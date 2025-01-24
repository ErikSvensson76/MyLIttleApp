package org.example.mylittleapp.service.dao;

import jakarta.validation.constraints.NotNull;
import org.example.mylittleapp.config.BaseUrl;
import org.example.mylittleapp.exception.NoEntityFoundException;
import org.example.mylittleapp.exception.RuntimeIOException;
import org.example.mylittleapp.model.entity.EntityBinary;
import org.example.mylittleapp.model.entity.EntityMarkdown;
import org.example.mylittleapp.service.repos.EntityBinaryRepo;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
@Validated
public class EntityFileService {

  private final EntityBinaryRepo repository;
  private final EntityMarkdownService markdownService;
  private final BaseUrl baseUrl;

  public EntityFileService(EntityBinaryRepo repository, EntityMarkdownService markdownService, BaseUrl baseUrl) {
    this.repository = repository;
    this.markdownService = markdownService;
    this.baseUrl = baseUrl;
  }


  public EntityBinary findById(String id) {
    return repository.findById(id)
        .orElseThrow(() -> new NoEntityFoundException("No entity of type EntityBinary found with id " + id));
  }


  public List<EntityBinary> findAll() {
    return repository.findAll();
  }


  public EntityBinary save(@NotNull MultipartFile multipartFile) {
    if(multipartFile.isEmpty()) throw new IllegalArgumentException("MultipartFile is empty");
    if(multipartFile.getContentType() == null) throw new IllegalArgumentException("MultipartFile content type is null");
    if(!multipartFile.getContentType().startsWith("image")) {
      throw new IllegalArgumentException("Only images supported");
    }

    Resource resource = multipartFile.getResource();
    byte[] bytes;
    EntityBinary binary;

    try(BufferedInputStream inputStream = new BufferedInputStream(resource.getInputStream())){
      bytes = inputStream.readAllBytes();
    }catch (Exception e){
      throw new RuntimeIOException(e.getMessage(),e);
    }

    binary = repository.findByFileName(multipartFile.getOriginalFilename())
        .orElse(null);
    if(binary == null){
      binary = new EntityBinary();
    }
    binary.setFileName(multipartFile.getOriginalFilename());
    binary.setMimeType(multipartFile.getContentType());
    binary.setBinaryData(bytes);
    String originalFilename = multipartFile.getOriginalFilename();
    if(originalFilename != null && !originalFilename.isEmpty()){
      binary.setFileName(originalFilename);
    }else{
      throw new IllegalArgumentException("Filename is null or empty");
    }
    String path = Paths.get(baseUrl.url(),"asset",originalFilename).toString();
    binary.setUrl(path);

    return repository.save(binary);
  }

  public EntityBinary save(@NotNull MultipartFile multipartFile, @NotNull String parentId){
    EntityBinary binary = save(multipartFile);
    EntityMarkdown markdown = markdownService.findById(parentId);
    binary.setParent(markdown);
    return repository.save(binary);
  }


  public List<EntityBinary> saveAll(List<MultipartFile> multipartFiles) {
    if(multipartFiles == null) throw new IllegalArgumentException("MultipartFiles is null");
    return multipartFiles.stream()
        .filter(Objects::nonNull)
        .map(this::save)
        .collect(Collectors.toList());
  }


  public boolean delete(String id) {
    EntityBinary entityBinary = findById(id);
    entityBinary.setParent(null);
    repository.delete(entityBinary);
    return repository.findById(id).isEmpty();
  }


  public boolean deleteAll(List<String> ids) {
    if(ids == null) throw new IllegalArgumentException("ids cannot be null");
    return ids.stream()
        .filter(Objects::nonNull)
        .map(this::delete)
        .allMatch(bool -> bool.equals(true));
  }


  public EntityBinary findByFileName(String fileName) {
    return repository.findByFileName(fileName)
        .orElseThrow(() -> new NoEntityFoundException("No entity of type EntityBinary found with fileName: " + fileName));
  }


  public List<EntityBinary> findByParentId(String id) {
    return repository.findByParentId(id);
  }
}
