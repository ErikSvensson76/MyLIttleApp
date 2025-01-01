package org.example.mylittleapp.service.generic;


import java.util.List;

public interface GenericFetchService <ENTITY>{

  ENTITY findById(String id);

  List<ENTITY> findAll();

}
