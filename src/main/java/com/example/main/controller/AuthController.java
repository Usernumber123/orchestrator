package com.example.main.controller;
import com.example.main.api.AuthApi;
import com.example.main.model.UserDto;
import com.example.main.security.AuthService;
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
    public ResponseEntity<String> signUp(UserDto userDto) {
        return new ResponseEntity(authService.signUp(userDto), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> token(UserDto userDto) {
        return new ResponseEntity(authService.generateToken(userDto), HttpStatus.OK);
    }
}