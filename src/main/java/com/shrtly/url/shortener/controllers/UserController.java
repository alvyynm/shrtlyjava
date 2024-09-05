package com.shrtly.url.shortener.controllers;

import com.shrtly.url.shortener.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
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
