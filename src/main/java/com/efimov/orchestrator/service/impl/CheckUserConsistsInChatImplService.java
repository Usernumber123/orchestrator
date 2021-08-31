package com.efimov.orchestrator.service.impl;

import com.efimov.orchestrator.domain.entity.Chat;
import com.efimov.orchestrator.domain.entity.User;
import com.efimov.orchestrator.repository.UserRepository;
import com.efimov.orchestrator.service.CheckUserConsistsInChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CheckUserConsistsInChatImplService implements CheckUserConsistsInChatService {
    private final UserRepository userRepository;


    @Override
    public boolean checkUserConsistsInChatBool(String userLogin,String chatName) {
        User user = userRepository.findOneByLogin(userLogin).orElseThrow();
        if (!user.getJoinChats().isEmpty()) {
            for (Chat chat : new ArrayList<>(user.getJoinChats())) {
                if (chat.getName().equals(chatName)) return true;
            }
        }
        return false;

    }
}
