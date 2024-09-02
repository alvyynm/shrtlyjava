package com.shrtly.url.shortener.controllers;

import com.shrtly.url.shortener.models.Url;
import com.shrtly.url.shortener.services.UrlService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class IndexController {
    private final UrlService urlService;

    public IndexController(UrlService urlService) {
        this.urlService = urlService;
    }

    @GetMapping("/{urlId}")
    public RedirectView getOriginalUrl(@PathVariable String urlId) {
        Url url = urlService.getUrl(urlId);
        if(url == null) {
            return null;

        }
        return new RedirectView(url.getOriginalUrl());
    }
}
