package org.example.mylittleapp.service.generic;

import java.util.List;

public interface GenericPersistenceService <INPUT,ENTITY>{

  ENTITY save(INPUT input);

  List<ENTITY> saveAll(List<INPUT> inputs);

  boolean delete(String id);

  boolean deleteAll(List<String> ids);

}
