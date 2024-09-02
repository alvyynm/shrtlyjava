package com.shrtly.url.shortener.services;

import com.shrtly.url.shortener.models.Url;
import com.shrtly.url.shortener.models.UrlStat;
import com.shrtly.url.shortener.repository.UrlRepository;
import com.shrtly.url.shortener.repository.UrlStatsRepository;
import org.springframework.stereotype.Service;
import com.aventrix.jnanoid.jnanoid.NanoIdUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UrlService {
    private static final char[] ALPHABET =
            "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();

    private static final int ID_LENGTH = 6;
    private final UrlRepository urlRepository;
    private final UrlStatsRepository urlStatsRepository;

    public UrlService(UrlRepository urlRepository, UrlStatsRepository urlStatsRepository) {
        this.urlRepository = urlRepository;
        this.urlStatsRepository = urlStatsRepository;
    }

    public Iterable<Url> getUrls() {
        return urlRepository.findAll();
    }

    public Url getUrl(String urlId) {
        Url url = urlRepository.findByUrlId(urlId);
        if (url != null) {
            incrementUrlStatsCount(url.getId());
        }
        return url;
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

    public Optional<UrlStat> getUrlStats(Integer urlId) {
        return urlStatsRepository.findById(urlId);
    }

    private void incrementUrlStatsCount(Integer urlId) {
        LocalDate today = LocalDate.now();
        List<UrlStat> urlStats = urlStatsRepository.findByUrlIdAndDate(urlId, today);

        if (urlStats.isEmpty()) {
            UrlStat stat = new UrlStat(urlId, today, 1);
            urlStatsRepository.save(stat);
        } else {
            UrlStat stat = urlStats.get(0);
            stat.setClickCount(stat.getClickCount() + 1);
            urlStatsRepository.save(stat);
        }
    }
}
