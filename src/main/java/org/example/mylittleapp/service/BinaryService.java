package org.example.mylittleapp.service;

import org.example.mylittleapp.model.dto.Binary;
import org.example.mylittleapp.service.generic.GenericFetchService;
import org.example.mylittleapp.service.generic.GenericPersistenceService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BinaryService extends
    GenericPersistenceService<MultipartFile, Binary>,
    GenericFetchService<Binary> {
  Binary findByFileName(String fileName);
  List<Binary> findByParentId(String id);
}
