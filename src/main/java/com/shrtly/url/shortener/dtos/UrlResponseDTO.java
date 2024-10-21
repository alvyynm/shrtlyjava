package com.shrtly.url.shortener.dtos;

public class UrlResponseDTO {
    private Integer id;
    private String urlId;
    private String originalUrl;
    private String shortenedUrl;
    private Integer userId;

    public UrlResponseDTO(Integer id, String urlId, String originalUrl, String shortenedUrl, Integer userId) {
        this.id = id;
        this.urlId = urlId;
        this.originalUrl = originalUrl;
        this.shortenedUrl = shortenedUrl;
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrlId() {
        return urlId;
    }

    public void setUrlId(String urlId) {
        this.urlId = urlId;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getShortenedUrl() {
        return shortenedUrl;
    }

    public void setShortenedUrl(String shortenedUrl) {
        this.shortenedUrl = shortenedUrl;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
