package com.shrtly.url.shortener.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "URLS")
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // For auto-increment behavior
    private Integer id;

    private String urlId;

    @NotEmpty
    private String originalUrl;

    private String shortenedUrl;

    // Getters and Setters
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
