package com.shrtly.url.shortener.models;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("URL")
public class Url {
    @Id
    private String urlId;

    @NotEmpty
    private String originalUrl;

    private String shortenedUrl;
}
