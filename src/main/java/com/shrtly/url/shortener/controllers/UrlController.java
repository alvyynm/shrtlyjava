package com.shrtly.url.shortener.controllers;

import com.shrtly.url.shortener.models.Url;
import com.shrtly.url.shortener.services.UrlService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping({"/api/v1", "/api/v1/"})
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
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
            @ApiResponse(responseCode = "405", description = "Unsupported HTTP method")
    })
    @PostMapping("/shorten")
    public Object shortenUrl(@RequestBody Map<String, String> payload) {
        String originalUrl = payload.get("originalUrl");
        Url responseData = urlService.createUrl(originalUrl);
        return new ResponseEntity<>(responseData, HttpStatus.CREATED);
    }

    @Operation(summary = "Get short url by urlId", description = "Returns a json with the resource")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the short url"),
            @ApiResponse(responseCode = "405", description = "Unsupported HTTP method")
    })
    @GetMapping("/urls/{urlId}")
    public Url getUrl(@Parameter(description = "urlId of the url to be searched") @PathVariable String urlId) {
        return urlService.getUrl(urlId);
    }

    @Operation(summary = "Delete short url by id", description = "Delete the resource with specified id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Supplied id is invalid"),
            @ApiResponse(responseCode = "405", description = "Unsupported HTTP method")
    })
    @DeleteMapping("/urls/{id}")
    public String deleteUrl(@Parameter(description = "id of the url to be deleted") @PathVariable Integer id) {
        return urlService.deleteUrl(id);
    }

    @Operation(summary = "Get all shortened urls", description = "Returns a json with all urls")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found all urls"),
            @ApiResponse(responseCode = "405", description = "Unsupported HTTP method")
    })
    @GetMapping("/urls")
    public Iterable<Url> getUrls() {
        return urlService.getUrls();
    }

}
