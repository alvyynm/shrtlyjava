package com.shrtly.url.shortener.controllers;

import com.shrtly.url.shortener.models.User;
import com.shrtly.url.shortener.services.UserService;
import com.shrtly.url.shortener.utils.StandardApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/auth/users/{id}")
    public ResponseEntity<StandardApiResponse<?>> getUserDetails(@PathVariable Integer id) {
        return new ResponseEntity<>(new StandardApiResponse<>(true, "User found", userService.findById(id)), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/auth/users")
    public ResponseEntity<StandardApiResponse<?>> getUsers() {
        return new ResponseEntity<>(new StandardApiResponse<>(true, "Users found", userService.findAll()), HttpStatus.OK);
    }

}
