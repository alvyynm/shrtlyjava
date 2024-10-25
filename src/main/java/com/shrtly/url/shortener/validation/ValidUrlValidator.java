package com.shrtly.url.shortener.validation;

import com.shrtly.url.shortener.interfaces.ValidUrl;
import jakarta.validation.ConstraintValidator;

// ValidUrl validator implementation
public class ValidUrlValidator implements ConstraintValidator<ValidUrl, String> {
    private static final String URL_REGEX =
            "^(https?:\\/\\/)?([\\da-z\\.-]+)\\.([a-z\\.]{2,6})([\\/\\w \\.-]*)*\\/?$";

    @Override
    public void initialize(ValidUrl constraintAnnotation) {
        // Called once when validator is initialized
    }

    @Override
    public boolean isValid(String url, jakarta.validation.ConstraintValidatorContext context) {
        // This is where the actual validation happens
        // Returns true if valid, false if invalid
        if (url == null || url.trim().isEmpty()) {
            return false;
        }

        // Check for incomplete URLs like "http:" or "https://"
        if (url.equals("http:") || url.equals("https:") ||
                url.equals("http://") || url.equals("https://")) {
            return false;
        }

        try {
            // Create a java.net.URL object for basic validation
            new java.net.URL(url);

            // Additional regex validation for stricter format checking
            return url.matches(URL_REGEX);
        } catch (Exception e) {
            return false;
        }
    }
}
