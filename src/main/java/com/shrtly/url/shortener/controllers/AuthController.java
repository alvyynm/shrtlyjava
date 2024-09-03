package com.shrtly.url.shortener.controllers;

import com.shrtly.url.shortener.dtos.UserSignupDto;
import com.shrtly.url.shortener.models.User;
import com.shrtly.url.shortener.services.TokenService;
import com.shrtly.url.shortener.services.UserService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class AuthController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);

    private final TokenService tokenService;
    private final UserService userService;

    public AuthController(TokenService tokenService, UserService userService) {
        this.tokenService = tokenService;
        this.userService = userService;
    }

    @PostMapping("/token")
    public String token(Authentication authentication) {
        LOG.debug("Token request for user: '{}'", authentication.getName());
        String token = tokenService.generateToken(authentication);
        LOG.debug("Token granted {}", token);
        return token;
    }

    @PostMapping("/auth/signup")
    public ResponseEntity<UrlController.CommonApiResponse> signup(@Valid @RequestBody UserSignupDto userSignupDto) {
        return new ResponseEntity<>(new UrlController.CommonApiResponse(true, "User created successfully", userService.createUser(userSignupDto)), HttpStatus.CREATED);
    }

    @PostMapping("/auth/login")
    public String login(Authentication authentication) {
        // TODO: authenticate user and return jwt session
        return "jwt";
    }

    @GetMapping("/auth/user/{id}")
    public String getUserDetails(@PathVariable String id) {
        // TODO: Search for user with given id within db and return their info
        // User must be authenticated
        return "user";
    }

    @GetMapping("/auth/users")
    public ResponseEntity<UrlController.CommonApiResponse> getUsers() {
        // TODO: Get all users (ADMIN only)
        return new ResponseEntity<>(new UrlController.CommonApiResponse(true, "Users found", userService.findAll()), HttpStatus.OK);
    }
}
