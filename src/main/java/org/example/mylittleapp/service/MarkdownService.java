package org.example.mylittleapp.service;

import org.example.mylittleapp.model.dto.Markdown;
import org.example.mylittleapp.model.input.InputMarkdown;
import org.example.mylittleapp.service.generic.GenericFetchService;
import org.example.mylittleapp.service.generic.GenericPersistenceService;

public interface MarkdownService extends
    GenericPersistenceService<InputMarkdown, Markdown>,
    GenericFetchService<Markdown> {
}
