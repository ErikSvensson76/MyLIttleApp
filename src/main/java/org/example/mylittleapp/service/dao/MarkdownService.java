package org.example.mylittleapp.service.dao;

import org.example.mylittleapp.model.entity.EntityMarkdown;
import org.example.mylittleapp.model.input.InputMarkdown;
import org.example.mylittleapp.service.generic.GenericFetchService;
import org.example.mylittleapp.service.generic.GenericPersistenceService;

public interface MarkdownService extends
    GenericPersistenceService<InputMarkdown, EntityMarkdown>,
    GenericFetchService<EntityMarkdown>
{}
