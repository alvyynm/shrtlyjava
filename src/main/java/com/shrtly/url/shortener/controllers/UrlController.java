package com.shrtly.url.shortener.controllers;

import com.shrtly.url.shortener.models.Url;
import com.shrtly.url.shortener.services.UrlService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/urls/{urlId}")
    public Url getUrl(@PathVariable String urlId) {
        return urlService.getUrl(urlId);
    }

    @DeleteMapping("/urls/{id}")
    public String deleteUrl(@PathVariable Integer id) {
        return urlService.deleteUrl(id);
    }

    @GetMapping("/urls")
    public Iterable<Url> getUrls() {
        return urlService.getUrls();
    }

}
