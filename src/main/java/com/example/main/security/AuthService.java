package com.example.main.security;

import com.example.main.model.UserDto;
import com.example.main.domain.entity.User;
import com.example.main.repository.UserRepository;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService tokenService;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    public String generateToken(UserDto userDto) {
        User person = userRepository.findOneByLogin(userDto.getLogin()).orElseThrow(() -> new RuntimeException());
        if (!passwordEncoder.matches(userDto.getPassword(), person.getPassword())) {
            throw new RuntimeException();
        }
        return tokenService.generateToken(person);
    }
    @SneakyThrows
    public String signUp(UserDto signUpDto) {
        User user = new User();
        user.setLogin(signUpDto.getLogin());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        userRepository.save(user);
        kafkaTemplate.send("auth",new Gson().toJson(user));
        UserDto userDto=new UserDto();
        userDto.setLogin(signUpDto.getLogin());
        userDto.setPassword(signUpDto.getPassword());
        return generateToken(userDto);
    }
}