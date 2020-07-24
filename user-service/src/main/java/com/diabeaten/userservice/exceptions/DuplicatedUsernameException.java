package com.diabeaten.userservice.exceptions;

public class DuplicatedUsernameException extends RuntimeException {
    public DuplicatedUsernameException(String message) {
        super(message);
    }
}
