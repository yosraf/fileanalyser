package com.castlabs.fileanalyser.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {UrlValidator.class})
public @interface MP4 {

    String message() default "invalid url, only MP4 is supported";
    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
