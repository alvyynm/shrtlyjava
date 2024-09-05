package com.shrtly.url.shortener.dtos;

import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

public class UrlDto {
    @NotEmpty(message = "originalUrl is required")
    @URL(message = "URL must be a valid url")
    private String originalUrl;

    public String getOriginalUrl() {
        return originalUrl;
    }

}
