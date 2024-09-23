package com.shrtly.url.shortener.controllers;

import com.shrtly.url.shortener.dtos.*;
import com.shrtly.url.shortener.models.User;
import com.shrtly.url.shortener.services.JwtService;
import com.shrtly.url.shortener.services.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public ResponseEntity<SignupResponse> signup(@Valid @RequestBody UserSignupDto userSignupDto) {
        User newUser = userService.createUser(userSignupDto);

        if(newUser == null) {
            return new ResponseEntity<>(new SignupResponse(false, "Invalid email address", null), HttpStatus.BAD_REQUEST);
        }

        SignupResponseDTO signupResponseDTO = new SignupResponseDTO(
                newUser.getUserId(),
                newUser.getUsername(),
                newUser.getEmail(),
                newUser.getUserRole(),
                newUser.getFullName()
        );

        return new ResponseEntity<>(new SignupResponse(true, "User created successfully", signupResponseDTO), HttpStatus.CREATED);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginUserDto loginUserDto) {
        User loggedInUser = userService.login(loginUserDto);

        String jwtToken = jwtService.generateToken(loggedInUser);

        LoginResponseDTO loginResponseDTO = new LoginResponseDTO(
                jwtToken,
                jwtService.getExpirationTime()
        );
        return new ResponseEntity<>(new LoginResponse(true, "Successfully logged in", loginResponseDTO), HttpStatus.OK);
    }

    @GetMapping("/auth/me")
    public ResponseEntity<UserDetailsResponse> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();

        UserDetailsDTO userDetailsDTO = new UserDetailsDTO(
                currentUser.getUserId(),
                currentUser.getUsername(),
                currentUser.getEmail(),
                currentUser.getUserRole(),
                currentUser.getFullName()
        );
//        System.out.println("User " + currentUser);
        return new ResponseEntity<>(new UserDetailsResponse(true, "user details found", userDetailsDTO), HttpStatus.OK);
    }
}

class LoginResponse {
    private boolean status;
    private String message;
    private LoginResponseDTO data;

    public LoginResponse(boolean status, String message, LoginResponseDTO data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

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

    public LoginResponseDTO getData() {
        return data;
    }

    public void setData(LoginResponseDTO data) {
        this.data = data;
    }
}

class SignupResponse {
    private boolean success;
    private String message;
    private Object data;

    public SignupResponse(boolean success, String message, Object data) {
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


class UserDetailsResponse {
    private boolean success;
    private String message;
    private Object data;

    public UserDetailsResponse(boolean success, String message, Object data) {
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
