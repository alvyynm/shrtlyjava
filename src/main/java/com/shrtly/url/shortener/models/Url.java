package com.shrtly.url.shortener.models;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("URLS")
public class Url {
    @Id
    private Integer id;

    private String urlId;

    @NotEmpty
    private String originalUrl;

    private String shortenedUrl;

    public String getUrlId() {
        return urlId;
    }

    public Integer getId() {
        return id;
    }

    public void setUrlId(String urlId) {
        this.urlId = urlId;
    }

    public @NotEmpty String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(@NotEmpty String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getShortenedUrl() {
        return shortenedUrl;
    }

    public void setShortenedUrl(String shortenedUrl) {
        this.shortenedUrl = shortenedUrl;
    }
}
