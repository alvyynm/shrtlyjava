package com.shrtly.url.shortener.controllers;

import com.shrtly.url.shortener.models.Url;
import com.shrtly.url.shortener.services.UrlService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @GetMapping("/")
    public Map<String, String> index() {
        String key = "message";
        String value = "Hello World!";
        return Map.of(key, value);
    }

    @PostMapping("/shorten")
    public Object shortenUrl(@RequestBody Map<String, String> payload) {
        String originalUrl = payload.get("originalUrl");
        Url responseData = urlService.createUrl(originalUrl);
        return new ResponseEntity<>(responseData, HttpStatus.CREATED);
    }

}
