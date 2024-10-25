package com.shrtly.url.shortener.interfaces;

import com.shrtly.url.shortener.validation.ValidUrlValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

// annotation interface for ValidUrl custom validator
@Documented // Makes annotation visible in javadocs
@Constraint(validatedBy = ValidUrlValidator.class) // Links to the validator implementation
@Target({ElementType.FIELD, ElementType.PARAMETER}) // Where the annotation can be used
@Retention(RetentionPolicy.RUNTIME) // Annotation is available at runtime
public @interface ValidUrl {
    // Required by the validation framework
    String message() default "Invalid URL format";  // Default error message
    Class<?>[] groups() default {};                 // For grouping validations
    Class<? extends Payload>[] payload() default {}; // For additional metadata
}
