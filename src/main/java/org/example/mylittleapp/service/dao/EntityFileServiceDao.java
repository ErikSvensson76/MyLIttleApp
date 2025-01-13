package org.example.mylittleapp.service.dao;

import jakarta.validation.constraints.NotNull;
import org.example.mylittleapp.config.BaseUrl;
import org.example.mylittleapp.exception.NoEntityFoundException;
import org.example.mylittleapp.exception.RuntimeIOException;
import org.example.mylittleapp.model.entity.EntityBinary;
import org.example.mylittleapp.service.repos.EntityBinaryRepo;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
@Validated
public class EntityFileServiceDao implements EntityFileService {

  private final EntityBinaryRepo repository;
  private final BaseUrl baseUrl;

  public EntityFileServiceDao(EntityBinaryRepo repository, BaseUrl baseUrl) {
    this.repository = repository;
    this.baseUrl = baseUrl;
  }

  @Override
  public EntityBinary findById(String id) {
    return repository.findById(id)
        .orElseThrow(() -> new NoEntityFoundException("No entity of type EntityBinary found with id " + id));
  }

  @Override
  public List<EntityBinary> findAll() {
    return repository.findAll();
  }

  @Override
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
    binary.setUrl(baseUrl.url()+ "/asset/" + multipartFile.getOriginalFilename());
    return repository.save(binary);
  }

  @Override
  public List<EntityBinary> saveAll(List<MultipartFile> multipartFiles) {
    if(multipartFiles == null) throw new IllegalArgumentException("MultipartFiles is null");
    return multipartFiles.stream()
        .filter(Objects::nonNull)
        .map(this::save)
        .collect(Collectors.toList());
  }

  @Override
  public boolean delete(String id) {
    EntityBinary entityBinary = findById(id);
    entityBinary.setParent(null);
    repository.delete(entityBinary);
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
  public EntityBinary findByFileName(String fileName) {
    return repository.findByFileName(fileName)
        .orElseThrow(() -> new NoEntityFoundException("No entity of type EntityBinary found with fileName: " + fileName));
  }

  @Override
  public List<EntityBinary> findByParentId(String id) {
    return repository.findByParentId(id);
  }
}
