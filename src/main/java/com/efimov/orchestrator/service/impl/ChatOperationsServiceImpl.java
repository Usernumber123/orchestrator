package com.efimov.orchestrator.service.impl;

import com.efimov.orchestrator.domain.entity.Chat;
import com.efimov.orchestrator.domain.entity.User;
import com.efimov.orchestrator.exceptions.ChatNotFoundException;
import com.efimov.orchestrator.exceptions.UserDoesNotHaveAccess;
import com.efimov.orchestrator.repository.ChatRepository;
import com.efimov.orchestrator.repository.UserRepository;
import com.efimov.orchestrator.security.UserDetailsImpl;
import com.efimov.orchestrator.service.ChatOperationsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.NoSuchElementException;


@Service
@RequiredArgsConstructor
public class ChatOperationsServiceImpl implements ChatOperationsService {
    private final UserRepository userRepository;
    private final ChatRepository chatRepository;

    @Override
    public void createChat(String chat, Integer age) {
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Chat chat1 = new Chat();
        chat1.setHost(principal.getUser().getLogin());
        chat1.setAge(age);
        chat1.setName(chat);
        try {
            chatRepository.save(chat1);
        } catch (Exception e) {
            throw new UserDoesNotHaveAccess("Chat already exist");
        }
        try {
            addUserInChat(principal.getUser().getLogin(), chat);
        } catch (UserDoesNotHaveAccess userDoesNotHaveAccess) {
            chatRepository.delete(chat1);
            throw new UserDoesNotHaveAccess("User too young");
        }
    }

    private void addUserInChat(String userLogin, String chatName) {
        User user = userRepository.findOneByLogin(userLogin).orElseThrow();
        Chat chat = chatRepository.findOneByName(chatName).orElseThrow();
        if (user.getAge() >= chat.getAge()) {
            user.getJoinChats().add(chat);
            userRepository.save(user);
        } else throw new UserDoesNotHaveAccess("User too young");
    }

    @Override
    public void addNewUsersInChat(String userLogin, String chatName) {

        if (principalUserContainsInChat(chatName)) {
            try {
                addUserInChat(userLogin, chatName);

            } catch (NoSuchElementException e) {
                throw new NoSuchElementException("User not Found");
            }
        }
    }

    private boolean principalUserContainsInChat(String chatName) {
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findOneByLogin(principal.getUser().getLogin()).orElseThrow();
        if (!user.getJoinChats().isEmpty()) {
            for (Chat chat : new ArrayList<>(user.getJoinChats())) {
                if (chat.getName().equals(chatName)) return true;
            }
        }
        throw new ChatNotFoundException("Principal User does not consists in this chat");
    }
}
