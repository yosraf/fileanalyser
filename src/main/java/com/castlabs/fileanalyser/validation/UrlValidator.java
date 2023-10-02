package com.castlabs.fileanalyser.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

public class UrlValidator implements ConstraintValidator<URL, String> {
    @Override
    public boolean isValid(String url, ConstraintValidatorContext constraintValidatorContext) {
        try {
            new java.net.URL(url).toURI();
            return true;
        } catch (MalformedURLException | URISyntaxException e) {
            return false;
        }
    }
}
