package com.shrtly.url.shortener.controllers;

import com.shrtly.url.shortener.dtos.*;
import com.shrtly.url.shortener.models.User;
import com.shrtly.url.shortener.services.JwtService;
import com.shrtly.url.shortener.services.UserService;
import com.shrtly.url.shortener.utils.StandardApiResponse;
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
    public ResponseEntity<StandardApiResponse<SignupResponseDTO>> signup(@Valid @RequestBody UserSignupDto userSignupDto) {
        User newUser = userService.createUser(userSignupDto);

        if(newUser == null) {
            return new ResponseEntity<>(new StandardApiResponse<>(false, "Invalid email address", null), HttpStatus.BAD_REQUEST);
        }

        SignupResponseDTO signupResponseDTO = new SignupResponseDTO(
                newUser.getUserId(),
                newUser.getUsername(),
                newUser.getEmail(),
                newUser.getUserRole(),
                newUser.getFullName()
        );

        return new ResponseEntity<>(new StandardApiResponse<>(true, "User created successfully", signupResponseDTO), HttpStatus.CREATED);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<StandardApiResponse<LoginResponseDTO>> login(@Valid @RequestBody LoginUserDto loginUserDto) {
        User loggedInUser = userService.login(loginUserDto);

        String jwtToken = jwtService.generateToken(loggedInUser);

        LoginResponseDTO loginResponseDTO = new LoginResponseDTO(
                jwtToken,
                jwtService.getExpirationTime()
        );
        return new ResponseEntity<>(new StandardApiResponse<>(true, "Successfully logged in", loginResponseDTO), HttpStatus.OK);
    }

    @PostMapping("/auth/logout")
    public ResponseEntity<StandardApiResponse<?>> logout() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return new ResponseEntity<>(new StandardApiResponse<>(true, "Successfully logged out", null), HttpStatus.OK);
    }

    @GetMapping("/auth/me")
    public ResponseEntity<StandardApiResponse<UserDetailsResponseDTO>> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();

        UserDetailsResponseDTO userDetailsDTO = new UserDetailsResponseDTO(
                currentUser.getUserId(),
                currentUser.getUsername(),
                currentUser.getEmail(),
                currentUser.getUserRole(),
                currentUser.getFullName()
        );
//        System.out.println("User " + currentUser);
        return new ResponseEntity<>(new StandardApiResponse<>(true, "user details found", userDetailsDTO), HttpStatus.OK);
    }
}
