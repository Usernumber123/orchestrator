package com.efimov.orchestrator.security;

import com.efimov.orchestrator.domain.entity.Chat;
import com.efimov.orchestrator.model.UserDto;
import com.efimov.orchestrator.domain.entity.User;
import com.efimov.orchestrator.repository.ChatRepository;
import com.efimov.orchestrator.repository.UserRepository;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class AuthService {
    public static final String CHAT_ALL = "all";
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService tokenService;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ChatRepository chatRepository;

    public String generateToken(UserDto userDto) {
        User person = userRepository.findOneByLogin(userDto.getLogin()).orElseThrow(RuntimeException::new);
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
        user.setAge(signUpDto.getAge());
        HashSet<Chat> chats = new HashSet<>();
        Chat chat = chatRepository.findOneByName(CHAT_ALL).orElseThrow();
        chats.add(chat);
        user.setJoinChats(chats);
        userRepository.save(user);
        user.setJoinChats(null);
        UserDto userDto = new UserDto();
        userDto.setLogin(signUpDto.getLogin());
        userDto.setAge(signUpDto.getAge());
        userDto.setPassword(signUpDto.getPassword());
        kafkaTemplate.send("auth", user);
        return generateToken(userDto);
    }
}