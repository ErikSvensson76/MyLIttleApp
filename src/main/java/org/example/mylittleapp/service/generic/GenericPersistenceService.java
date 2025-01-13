package org.example.mylittleapp.service.generic;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface GenericPersistenceService <INPUT,ENTITY>{

  ENTITY save(@NotNull INPUT input);

  List<ENTITY> saveAll(List<INPUT> inputs);

  boolean delete(String id);

  boolean deleteAll(List<String> ids);

}
