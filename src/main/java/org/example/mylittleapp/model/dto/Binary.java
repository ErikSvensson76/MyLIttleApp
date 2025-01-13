package org.example.mylittleapp.model.dto;

import org.springframework.core.io.Resource;

import java.io.Serializable;

public record Binary(
    String id,
    String fileName,
    String mimeType,
    String url,
    Resource binaryData
) implements Serializable {}
