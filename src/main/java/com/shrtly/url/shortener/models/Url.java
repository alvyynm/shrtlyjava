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

    public String getUrlId() {
        return urlId;
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
