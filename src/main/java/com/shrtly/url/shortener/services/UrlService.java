package com.shrtly.url.shortener.services;

import com.shrtly.url.shortener.dtos.UrlResponseDTO;
import com.shrtly.url.shortener.exceptions.UrlNotFoundException;
import com.shrtly.url.shortener.models.Url;
import com.shrtly.url.shortener.models.UrlStat;
import com.shrtly.url.shortener.models.User;
import com.shrtly.url.shortener.repository.UrlRepository;
import com.shrtly.url.shortener.repository.UrlStatsRepository;
import com.shrtly.url.shortener.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import com.aventrix.jnanoid.jnanoid.NanoIdUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UrlService {
    private static final char[] ALPHABET =
            "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();

    private static final int ID_LENGTH = 6;
    private final UrlRepository urlRepository;
    private final UrlStatsRepository urlStatsRepository;
    private final UserRepository userRepository;

    public UrlService(UrlRepository urlRepository, UrlStatsRepository urlStatsRepository, UserRepository userRepository) {
        this.urlRepository = urlRepository;
        this.urlStatsRepository = urlStatsRepository;
        this.userRepository = userRepository;
    }

    public List<UrlResponseDTO> getUrls() {
        Iterable<Url> urls = urlRepository.findAll();
        List<UrlResponseDTO> urlResponseDTOs = StreamSupport.stream(urls.spliterator(), false)
                .map(url -> new UrlResponseDTO(
                        url.getId(),
                        url.getUrlId(),
                        url.getOriginalUrl(),
                        url.getShortenedUrl(),
                        url.getUser().getUserId()))
                .collect(Collectors.toList());
        urls.forEach(url -> {});
        return urlResponseDTOs;
    }

    public UrlResponseDTO getUrl(String urlId) {
        Url url = urlRepository.findByUrlId(urlId);

        if (url == null) {
           throw new UrlNotFoundException("URL not found for ID: " + urlId);
        }

        incrementUrlStatsCount(url.getId());

        return new UrlResponseDTO(url.getId(), url.getUrlId(), url.getOriginalUrl(), url.getShortenedUrl(), url.getUser().getUserId());
    }

    public UrlResponseDTO createUrl(String originalUrl) {
        // Retrieve the authenticated user's email from the SecurityContext
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Find the user in db using the email
        User currentUser = userRepository.findByEmail(userDetails.getUsername()).orElseThrow(() -> new IllegalArgumentException("Invalid user email"));

        // Create a new Url entity and set the necessary fields
        Url url = new Url();
        String newUrlId = NanoIdUtils.randomNanoId(NanoIdUtils.DEFAULT_NUMBER_GENERATOR, ALPHABET, ID_LENGTH);
        url.setOriginalUrl(originalUrl);
        url.setUrlId(newUrlId);
        url.setShortenedUrl("http://localhost:8080/" + newUrlId);

        // Set the user on the Url entity (establish the relationship)
        url.setUser(currentUser);

        urlRepository.save(url);
        return new UrlResponseDTO(url.getId(), url.getUrlId(), url.getOriginalUrl(), url.getShortenedUrl(), url.getUser().getUserId());
    }

    public Url getUrlById(Integer id) {
        Url url = urlRepository.findById(id).orElseThrow(() -> new UrlNotFoundException("URL not found for ID: " + id));
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

    public Iterable<UrlStat> getUrlStats(Integer urlId) {
        return urlStatsRepository.findByUrlId(urlId);
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
