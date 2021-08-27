package com.efimov.orchestrator.exceptions.handler;

import com.efimov.orchestrator.exceptions.ChatNotFoundException;
import com.efimov.orchestrator.exceptions.UserDoesNotHaveAccess;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@RestController
@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(ChatNotFoundException.class)
    public String handleChatNotFoundException(ChatNotFoundException chatNotFoundException) {
        return chatNotFoundException.getMessage();
    }

    @ExceptionHandler(UserDoesNotHaveAccess.class)
    public String handleUserDoesNotHaveAccess(UserDoesNotHaveAccess userDoesNotHaveAccess) {
        return userDoesNotHaveAccess.getMessage();
    }

    @ExceptionHandler(NoSuchElementException.class)
    public String handleNoSuchElementException(NoSuchElementException noSuchElementException) {
        return noSuchElementException.getMessage();
    }
}
