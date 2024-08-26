package com.shrtly.url.shortener.services;

import com.shrtly.url.shortener.models.Url;
import com.shrtly.url.shortener.repository.UrlRepository;
import org.springframework.stereotype.Service;
import com.aventrix.jnanoid.jnanoid.NanoIdUtils;

@Service
public class UrlService {
    private static final char[] ALPHABET =
            "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();

    private static final int ID_LENGTH = 6;
    private final UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public Iterable<Url> getUrls() {
        return urlRepository.findAll();
    }

    public Url getUrl(String urlId) {
        return urlRepository.findByUrlId(urlId);
    }

    public Url createUrl(String originalUrl) {
        Url url = new Url();
        String newUrlId = NanoIdUtils.randomNanoId(NanoIdUtils.DEFAULT_NUMBER_GENERATOR, ALPHABET, ID_LENGTH);
        url.setOriginalUrl(originalUrl);
        url.setUrlId(newUrlId);
        url.setShortenedUrl("http://localhost:8080/" + newUrlId);
        urlRepository.save(url);
        return url;
    }

    public String deleteUrl(Integer id) {
        Url url = urlRepository.findById(id).orElse(null);
        urlRepository.deleteById(id);
        if(url == null) {
            return null;
        } else {
            return "successfully deleted url";
        }
    }
}
