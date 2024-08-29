package com.shrtly.url.shortener.controllers;

import com.shrtly.url.shortener.dtos.UrlDto;
import com.shrtly.url.shortener.models.Url;
import com.shrtly.url.shortener.services.UrlService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
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
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "405", description = "Unsupported HTTP method")
    })
    @PostMapping("/shorten")
    public ResponseEntity<CommonApiResponse> shortenUrl(@RequestBody UrlDto payload) {
        // check if originalUrl is present
        if(payload.getOriginalUrl() == null || payload.getOriginalUrl().isEmpty()) {
            return new ResponseEntity<>(new CommonApiResponse(false, "originalUrl is required", null), HttpStatus.BAD_REQUEST);
        }

        // validate url
        if(!isValidUrl(payload.getOriginalUrl())) {
            return new ResponseEntity<>(new CommonApiResponse(false, "Invalid URL", null), HttpStatus.BAD_REQUEST);
        }

        Url responseData = urlService.createUrl(payload.getOriginalUrl());
        return new ResponseEntity<>(new CommonApiResponse(true, "Url created successfully", responseData), HttpStatus.CREATED);
    }

    @Operation(summary = "Get short url by urlId", description = "Returns a json with the resource")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the short url"),
            @ApiResponse(responseCode = "404", description = "Url not found"),
            @ApiResponse(responseCode = "405", description = "Unsupported HTTP method")
    })
    @GetMapping("/urls/{urlId}")
    public ResponseEntity<CommonApiResponse> getUrl(@Parameter(description = "urlId of the url to be searched") @PathVariable String urlId) {
        Url url = urlService.getUrl(urlId);
        if(url == null) {
            return new ResponseEntity<>(new CommonApiResponse(false, "Url not found", null), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new CommonApiResponse(true, "Url found", url), HttpStatus.OK);
    }

    @Operation(summary = "Delete short url by id", description = "Delete the resource with specified id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Supplied id is invalid"),
            @ApiResponse(responseCode = "405", description = "Unsupported HTTP method")
    })
    @DeleteMapping("/urls/{id}")
    public ResponseEntity<CommonApiResponse> deleteUrl(@Parameter(description = "id of the url to be deleted") @PathVariable Integer id) {
        String response = urlService.deleteUrl(id);

        if(response == null) {
            return new ResponseEntity<>(new CommonApiResponse(false, "Url not found", null), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new CommonApiResponse(true, "Url deleted successfully", urlService.deleteUrl(id)), HttpStatus.OK);
    }

    @Operation(summary = "Get all shortened urls", description = "Returns a json with all urls")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found all urls"),
            @ApiResponse(responseCode = "405", description = "Unsupported HTTP method")
    })
    @GetMapping("/urls")
    public ResponseEntity<CommonApiResponse> getUrls() {
        return new ResponseEntity<>(new CommonApiResponse(true, "All urls found", urlService.getUrls()), HttpStatus.OK);
    }

    private boolean isValidUrl(String url){
        try{
            new URL(url).toURI();
            return true;
        } catch (URISyntaxException | MalformedURLException e) {
            return false;
        }
    }

    public static class CommonApiResponse {
        private boolean success;
        private String message;
        private Object data;

        public CommonApiResponse(boolean success, String message, Object data) {
            this.success = success;
            this.message = message;
            this.data = data;
        }

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }
    }
}
