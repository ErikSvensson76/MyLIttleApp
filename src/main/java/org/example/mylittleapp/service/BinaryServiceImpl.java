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
public class BinaryServiceImpl extends DtoConverterService implements BinaryService {

  private final EntityFileService entityFileService;


  public BinaryServiceImpl(EntityFileService entityFileService) {
    this.entityFileService = entityFileService;
  }

  @Override
  public Binary findByFileName(String fileName) {
    return entityToBinary(entityFileService.findByFileName(fileName));
  }

  @Override
  public List<Binary> findByParentId(String id) {
    return entityFileService.findByParentId(id).stream()
        .filter(Objects::nonNull)
        .map(this::entityToBinary)
        .toList();
  }

  @Override
  public Binary findById(String id) {
    return entityToBinary(entityFileService.findById(id));
  }

  @Override
  public List<Binary> findAll() {
    return entityFileService.findAll().stream()
        .filter(Objects::nonNull)
        .map(this::entityToBinary)
        .toList();
  }

  @Override
  public Binary save(MultipartFile multipartFile) {
    return entityToBinary(entityFileService.save(multipartFile));
  }

  @Override
  public List<Binary> saveAll(List<MultipartFile> multipartFiles) {
    return entityFileService.saveAll(multipartFiles).stream()
        .filter(Objects::nonNull)
        .map(this::entityToBinary)
        .toList();
  }

  @Override
  public boolean delete(String id) {
    return entityFileService.delete(id);
  }

  @Override
  public boolean deleteAll(List<String> ids) {
    return entityFileService.deleteAll(ids);
  }
}
