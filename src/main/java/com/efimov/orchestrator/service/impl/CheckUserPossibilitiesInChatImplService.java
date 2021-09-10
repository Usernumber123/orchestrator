package com.efimov.orchestrator.service.impl;

import com.efimov.orchestrator.domain.entity.Chat;
import com.efimov.orchestrator.domain.entity.User;
import com.efimov.orchestrator.repository.UserRepository;
import com.efimov.orchestrator.service.CheckUserPossibilitiesInChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CheckUserPossibilitiesInChatImplService implements CheckUserPossibilitiesInChatService {
    private final UserRepository userRepository;

    @Override
    public boolean checkUserConsistsInChat(String userLogin, String chatName) {
        User user = userRepository.findOneByLogin(userLogin).orElseThrow();
        if (!user.getJoinChats().isEmpty()) {
            for (Chat chat : new ArrayList<>(user.getJoinChats())) {
                if (chat.getName().equals(chatName))
                    return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkUserOnHost(String userLogin, String chatName) {
        User user = userRepository.findOneByLogin(userLogin).orElseThrow();
        if (!user.getJoinChats().isEmpty()) {
            for (Chat chat : new ArrayList<>(user.getJoinChats())) {
                if (chat.getName().equals(chatName) && chat.getHost().equals(userLogin))
                    return true;
            }
        }
        return false;
    }
}
