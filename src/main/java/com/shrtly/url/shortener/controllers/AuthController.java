package com.shrtly.url.shortener.controllers;

import com.shrtly.url.shortener.dtos.LoginUserDto;
import com.shrtly.url.shortener.dtos.UserSignupDto;
import com.shrtly.url.shortener.models.User;
import com.shrtly.url.shortener.services.JwtService;
import com.shrtly.url.shortener.services.UserService;
import jakarta.validation.Valid;
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

    private final JwtService jwtService;
    private final UserService userService;

    public AuthController(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @PostMapping("/auth/signup")
    public ResponseEntity<UrlController.CommonApiResponse> signup(@Valid @RequestBody UserSignupDto userSignupDto) {
        return new ResponseEntity<>(new UrlController.CommonApiResponse(true, "User created successfully", userService.createUser(userSignupDto)), HttpStatus.CREATED);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginUserDto loginUserDto) {
        User loggedInUser = userService.login(loginUserDto);

        String jwtToken = jwtService.generateToken(loggedInUser);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setStatus(true);
        loginResponse.setData(null);
        loginResponse.setMessage("Successfully logged in");
        loginResponse.setToken(jwtToken);
        loginResponse.setData(null);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

    @GetMapping("/auth/users/{id}")
    public ResponseEntity<UrlController.CommonApiResponse> getUserDetails(@PathVariable Integer id) {
        // TODO: Search for user with given id within db and return their info
        // User must be authenticated
        return new ResponseEntity<>(new UrlController.CommonApiResponse(true, "User found", userService.findById(id)), HttpStatus.OK);
    }

    @GetMapping("/auth/users")
    public ResponseEntity<UrlController.CommonApiResponse> getUsers() {
        // TODO: Get all users (ADMIN only)
        return new ResponseEntity<>(new UrlController.CommonApiResponse(true, "Users found", userService.findAll()), HttpStatus.OK);
    }
}

class LoginResponse {
    private boolean status;
    private String message;
    private String data;
    private String token;
    private long expiresIn;

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }
}
