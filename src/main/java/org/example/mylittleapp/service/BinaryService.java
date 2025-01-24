package org.example.mylittleapp.service;

import org.example.mylittleapp.model.dto.Binary;
import org.example.mylittleapp.service.dao.EntityFileService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class BinaryService extends DtoConverterService {

  private final EntityFileService entityFileService;


  public BinaryService(EntityFileService entityFileService) {
    this.entityFileService = entityFileService;
  }


  public Binary findByFileName(String fileName) {
    return entityToBinary(entityFileService.findByFileName(fileName));
  }


  public List<Binary> findByParentId(String id) {
    return entityFileService.findByParentId(id).stream()
        .filter(Objects::nonNull)
        .map(this::entityToBinary)
        .toList();
  }


  public String saveBinary(String parentId, MultipartFile file) {
    return entityFileService.save(file,parentId).getUrl();
  }


  public Binary findById(String id) {
    return entityToBinary(entityFileService.findById(id));
  }


  public List<Binary> findAll() {
    return entityFileService.findAll().stream()
        .filter(Objects::nonNull)
        .map(this::entityToBinary)
        .toList();
  }


  public Binary save(MultipartFile multipartFile) {
    return entityToBinary(entityFileService.save(multipartFile));
  }


  public List<Binary> saveAll(List<MultipartFile> multipartFiles) {
    return entityFileService.saveAll(multipartFiles).stream()
        .filter(Objects::nonNull)
        .map(this::entityToBinary)
        .toList();
  }


  public boolean delete(String id) {
    return entityFileService.delete(id);
  }


  public boolean deleteAll(List<String> ids) {
    return entityFileService.deleteAll(ids);
  }
}
