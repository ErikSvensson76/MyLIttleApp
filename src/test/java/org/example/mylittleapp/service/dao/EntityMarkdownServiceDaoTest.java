package org.example.mylittleapp.service.dao;

import org.example.mylittleapp.TestUtils;
import org.example.mylittleapp.model.entity.EntityMarkdown;
import org.example.mylittleapp.model.input.InputMarkdown;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@Transactional
@DirtiesContext
class EntityMarkdownServiceDaoTest {

  private static final Logger log = LoggerFactory.getLogger(EntityMarkdownServiceDaoTest.class);
  @Autowired
  EntityMarkdownServiceDao underTest;
  @Autowired TestEntityManager em;
  TestUtils testUtils = TestUtils.getInstance();


  @Test
  void saveSimpleMarkdown() {
    InputMarkdown inputMarkdown = testUtils.getInputMarkdown();

    EntityMarkdown result = underTest.save(inputMarkdown);
    assertNotNull(result);
    assertNotNull(result.getId());
    assertEquals(inputMarkdown.getOrder(), result.getOrder());
    assertNotNull(result.getMarkdownContent());
    assertNull(result.getLesson());
  }



  @Test
  void saveMarkdownWithFile() {
    Resource resource = new ClassPathResource("test.md");
    assertNotNull(resource);
    MultipartFile file = null;
    try{
      file = new MockMultipartFile(
          "markdownTest",
          "test.md",
          "text/markdown",
          resource.getInputStream()
      );
    }catch (Exception e){
      log.error(e.getMessage());
    }
    assertNotNull(file);

    InputMarkdown input = new InputMarkdown();
    input.setOrder(1);
    input.setMarkdownFile(file);

    EntityMarkdown result = underTest.save(input);
    assertNotNull(result);
    assertNotNull(result.getId());
    assertEquals(input.getOrder(), result.getOrder());
    assertNotNull(result.getMarkdownContent());

  }

  @Test
  void saveAllMarkdown() {
    List<InputMarkdown> inputs = Stream.generate(testUtils::getInputMarkdown)
        .limit(5)
        .collect(Collectors.toList());

    List<EntityMarkdown> results = underTest.saveAll(inputs);
    assertNotNull(results);
    assertEquals(inputs.size(), results.size());
  }

  @Test
  void updateSimpleMarkdown(){
    EntityMarkdown markdown = em.persist(testUtils.getEntityMarkdown(false));
    InputMarkdown input = new InputMarkdown();
    input.setId(markdown.getId());
    input.setOrder(markdown.getOrder());
    input.setMarkdownContent(Arrays.asList(
        "# HelloWorld",
        "## Foo",
        "### Bar"
    ));

    EntityMarkdown result = underTest.save(input);
    assertNotNull(result);
    assertEquals(input.getOrder(), result.getOrder());
    assertEquals("# HelloWorld\n## Foo\n### Bar", result.getMarkdownContent());
    assertNull(result.getLesson());
  }

  @Test
  void findAllMarkdown() {
    em.persist(testUtils.getEntityMarkdown(false));
    assertEquals(1, underTest.findAll().size());
  }

  @Test
  void deleteMarkdown() {
    EntityMarkdown markdown = em.persist(testUtils.getEntityMarkdown(false));
    assertNotNull(markdown);

    String id = markdown.getId();
    assertTrue(underTest.delete(id));
  }
}