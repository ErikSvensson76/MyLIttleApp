package org.example.mylittleapp.service;

import org.example.mylittleapp.model.dto.Markdown;
import org.example.mylittleapp.model.entity.EntityBinary;
import org.example.mylittleapp.model.input.InputMarkdown;
import org.example.mylittleapp.service.dao.EntityFileService;
import org.example.mylittleapp.service.dao.EntityMarkdownService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class MarkdownServiceImpl extends DtoConverterService implements MarkdownService {

  private final EntityMarkdownService markdownService;
  private final EntityFileService entityFileService;

  public MarkdownServiceImpl(EntityMarkdownService markdownService, EntityFileService entityFileService) {
    this.markdownService = markdownService;
    this.entityFileService = entityFileService;
  }

  @Override
  public Markdown findById(String id) {
    EntityBinary[] entityBinaries = entityFileService.findByParentId(id)
        .toArray(EntityBinary[]::new);

    return entityToMarkdown(markdownService.findById(id), entityBinaries);
  }

  @Override
  public List<Markdown> findAll() {
    return markdownService.findAll().stream()
        .filter(Objects::nonNull)
        .map(this::entityToMarkdown)
        .toList();
  }

  @Override
  public Markdown save(InputMarkdown inputMarkdown) {
    return entityToMarkdown(markdownService.save(inputMarkdown));
  }

  @Override
  public List<Markdown> saveAll(List<InputMarkdown> inputMarkdowns) {
    return markdownService.saveAll(inputMarkdowns).stream()
        .filter(Objects::nonNull)
        .map(this::entityToMarkdown)
        .sorted(Comparator.comparing(Markdown::getOrder))
        .toList();
  }

  @Override
  public boolean delete(String id) {
    return markdownService.delete(id);
  }

  @Override
  public boolean deleteAll(List<String> ids) {
    return markdownService.deleteAll(ids);
  }
}
