package com.efimov.orchestrator.exceptions;

public class UserDoesNotHaveAccess extends RuntimeException {
    public UserDoesNotHaveAccess(String message) {
        super(message);
    }
}
