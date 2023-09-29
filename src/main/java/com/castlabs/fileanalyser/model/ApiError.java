package com.castlabs.fileanalyser.model;

import org.springframework.http.HttpStatus;

public record ApiError(HttpStatus status,String message) {
}
