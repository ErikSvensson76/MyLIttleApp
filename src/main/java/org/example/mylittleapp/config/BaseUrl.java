package org.example.mylittleapp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "base")
@Validated
public record BaseUrl(
    String url
) {}
