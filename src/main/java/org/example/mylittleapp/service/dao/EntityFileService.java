package org.example.mylittleapp.service.dao;

import org.example.mylittleapp.model.entity.EntityBinary;
import org.example.mylittleapp.service.generic.GenericFetchService;
import org.example.mylittleapp.service.generic.GenericPersistenceService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EntityFileService extends
    GenericPersistenceService<MultipartFile, EntityBinary>,
    GenericFetchService<EntityBinary> {

  EntityBinary findByFileName(String fileName);
  List<EntityBinary> findByParentId(String id);

}
