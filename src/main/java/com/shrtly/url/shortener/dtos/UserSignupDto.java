package com.shrtly.url.shortener.dtos;

import jakarta.validation.constraints.*;

public class UserSignupDto {
    @NotEmpty(message = "email is required")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "The email is not a valid email")
    private String email;

    @NotEmpty(message = "password is required")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!*()]).{8,20}$", message = "Password must be 8 to 20 characters long and combination of uppercase letters, lowercase letters, numbers, special characters.")
    private String password;

    @NotEmpty(message = "fullName is required")
    private String fullName;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }
}
