package com.diabeaten.edgeservice.exception;

public class AccessNotAllowedException extends RuntimeException {
    public AccessNotAllowedException(String message) {
        super(message);
    }
}
