package org.example.mylittleapp.service.dao;

import org.example.mylittleapp.model.entity.EntityBinary;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@Transactional
@DirtiesContext
class EntityFileServiceDaoTest {

  private static final Logger log = LoggerFactory.getLogger(EntityFileServiceDaoTest.class);
  @Autowired
  EntityFileServiceDao underTest;
  @Autowired TestEntityManager em;



  Resource resource = new ClassPathResource("test.jpg");

  MultipartFile getMultiPartFile() {
    try{
      return new MockMultipartFile(
          "testFile",
          "test.jpg",
          MediaType.IMAGE_JPEG_VALUE,
          resource.getInputStream()
      );
    }catch (IOException e){
      log.error(e.getMessage());
    }
    return null;
  }

  @Test
  void saveSingleFile(){
    EntityBinary result = underTest.save(getMultiPartFile());
    assertNotNull(result);
    assertNotNull(result.getId());
    assertEquals("test.jpg", result.getFileName());
    assertEquals("http://localhost:8080/asset/test.jpg", result.getUrl());
    assertNotNull(result.getBinaryData());
    assertNull(result.getParent());
  }

}