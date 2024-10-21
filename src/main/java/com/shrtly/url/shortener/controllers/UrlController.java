package com.shrtly.url.shortener.controllers;

import com.shrtly.url.shortener.dtos.UrlDto;
import com.shrtly.url.shortener.dtos.UrlResponseDTO;
import com.shrtly.url.shortener.models.Url;
import com.shrtly.url.shortener.models.UrlStat;
import com.shrtly.url.shortener.repository.UrlStatsRepository;
import com.shrtly.url.shortener.services.UrlService;
import com.shrtly.url.shortener.utils.StandardApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping({"/api/v1", "/api/v1/"})
public class UrlController {

    private final UrlService urlService;
    private final UrlStatsRepository urlStatsRepository;

    public UrlController(UrlService urlService, UrlStatsRepository urlStatsRepository) {
        this.urlService = urlService;
        this.urlStatsRepository = urlStatsRepository;
    }

    @Operation(summary = "Index route for api", description = "Returns a greeting message")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "405", description = "Unsupported HTTP method")
    })
    @GetMapping
    public Map<String, String> index() {
        String key = "message";
        String value = "Welcome! You're using v1 of the API";
        return Map.of(key, value);
    }

    @Operation(summary = "Shortens given url", description = "Returns a json with the created resource")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful url creation operation"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "405", description = "Unsupported HTTP method")
    })
    @PostMapping("/shorten")
    public ResponseEntity<StandardApiResponse<UrlResponseDTO>> shortenUrl(@Valid @RequestBody UrlDto payload) {
        UrlResponseDTO responseData = urlService.createUrl(payload.getOriginalUrl());
        return new ResponseEntity<>(new StandardApiResponse<>(true, "Url created successfully", responseData),
                HttpStatus.CREATED);
    }

    @Operation(summary = "Get short url by urlId", description = "Returns a json with the resource")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the short url"),
            @ApiResponse(responseCode = "404", description = "Url not found"),
            @ApiResponse(responseCode = "405", description = "Unsupported HTTP method")
    })
    @GetMapping("/urls/{urlId}")
    public ResponseEntity<StandardApiResponse<UrlResponseDTO>> getUrl(@Parameter(description = "urlId of the url to be searched") @PathVariable String urlId) {
        UrlResponseDTO url = urlService.getUrl(urlId);
        if(url == null) {
            return new ResponseEntity<>(new StandardApiResponse<>(false, "Url not found", null), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new StandardApiResponse<>(true, "Url found", url), HttpStatus.OK);
    }

    @Operation(summary = "Delete short url by id", description = "Delete the resource with specified id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Supplied id is invalid"),
            @ApiResponse(responseCode = "405", description = "Unsupported HTTP method")
    })
    @DeleteMapping("/urls/{id}")
    public ResponseEntity<StandardApiResponse<?>> deleteUrl(@Parameter(description = "id of the url to be deleted") @PathVariable Integer id) {
        String response = urlService.deleteUrl(id);

        if(response == null) {
            return new ResponseEntity<>(new StandardApiResponse<>(false, "Url not found", null), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new StandardApiResponse<>(true, "Url deleted successfully", urlService.deleteUrl(id)), HttpStatus.OK);
    }

    @Operation(summary = "Get all shortened urls", description = "Returns a json with all urls")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found all urls"),
            @ApiResponse(responseCode = "405", description = "Unsupported HTTP method")
    })
    @GetMapping("/urls")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StandardApiResponse<Iterable<UrlResponseDTO>>> getUrls() {
        List<UrlResponseDTO> urls = urlService.getUrls();

        return new ResponseEntity<>(new StandardApiResponse<>(true, "All urls found", urls), HttpStatus.OK);
    }

    @GetMapping("/urls/{id}/statistics")
    public ResponseEntity<StandardApiResponse<?>> getStatistics(@PathVariable Integer id) {
        Iterable<UrlStat> urlStat = urlService.getUrlStats(id);
        if(!urlStat.iterator().hasNext()) {
            return new ResponseEntity<>(new StandardApiResponse<>(false, "Url has no stats", null), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(new StandardApiResponse<>(true, "Url stats found", urlStat), HttpStatus.OK);
        }
    }
}
