package org.example.mylittleapp.service.dao;

import jakarta.validation.constraints.NotNull;
import org.example.mylittleapp.model.entity.EntityMarkdown;
import org.example.mylittleapp.model.input.InputMarkdown;
import org.example.mylittleapp.service.generic.GenericFetchService;
import org.example.mylittleapp.service.generic.GenericPersistenceService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EntityMarkdownService extends
    GenericPersistenceService<InputMarkdown, EntityMarkdown>,
    GenericFetchService<EntityMarkdown>
{
  List<String> readMultipartFile(@NotNull MultipartFile file);
}
