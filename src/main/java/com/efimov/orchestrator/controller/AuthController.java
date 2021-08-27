package com.efimov.orchestrator.controller;

import com.efimov.orchestrator.api.AuthApi;
import com.efimov.orchestrator.aspect.annotation.AspectLogger;
import com.efimov.orchestrator.model.UserDto;
import com.efimov.orchestrator.security.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping
@RequiredArgsConstructor
public class AuthController implements AuthApi {
    private final AuthService authService;

    @Override
    @AspectLogger
    public ResponseEntity<String> signUp(UserDto userDto) {
        return new ResponseEntity(authService.signUp(userDto), HttpStatus.OK);
    }

    @Override
    @AspectLogger
    public ResponseEntity<String> token(UserDto userDto) {
        return new ResponseEntity(authService.generateToken(userDto), HttpStatus.OK);
    }
}