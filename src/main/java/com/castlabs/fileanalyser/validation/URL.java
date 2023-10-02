package com.castlabs.fileanalyser.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {UrlValidator.class})
public @interface URL {
    String message() default "invalid url";
    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
