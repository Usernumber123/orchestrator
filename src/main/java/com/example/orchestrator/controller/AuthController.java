package com.example.orchestrator.controller;
import com.example.orchestrator.api.AuthApi;
import com.example.orchestrator.aspect.annotation.AspectLogger;
import com.example.orchestrator.model.UserDto;
import com.example.orchestrator.security.AuthService;
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