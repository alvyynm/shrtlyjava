package com.shrtly.url.shortener.models;

import jakarta.persistence.*;
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

    // Many URLs belong to one user
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User user;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
