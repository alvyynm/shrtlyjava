package com.shrtly.url.shortener.dtos;

import com.shrtly.url.shortener.interfaces.ValidUrl;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

public class UrlDto {
    @NotEmpty(message = "originalUrl is required")
    @ValidUrl(message = "URL must be a valid url")
    private String originalUrl;

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }
}
