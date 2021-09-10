package com.efimov.orchestrator.service.impl;

import com.efimov.orchestrator.domain.entity.Chat;
import com.efimov.orchestrator.domain.entity.User;
import com.efimov.orchestrator.exceptions.ChatNotFoundException;
import com.efimov.orchestrator.exceptions.UserDoesNotHaveAccess;
import com.efimov.orchestrator.repository.ChatRepository;
import com.efimov.orchestrator.repository.UserRepository;
import com.efimov.orchestrator.security.UserDetailsImpl;
import com.efimov.orchestrator.service.CheckUserPossibilitiesInChatService;
import com.efimov.orchestrator.service.UsersInChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
@Service
@RequiredArgsConstructor
public class UsersInChatServiceImpl implements UsersInChatService {
    private final UserRepository userRepository;
    private final ChatRepository chatRepository;
    private final CheckUserPossibilitiesInChatService checkUserPossibilitiesInChatService;

    @Override
    public void addNewUsersInChat(String userLogin, String chatName) {
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (checkUserPossibilitiesInChatService.checkUserConsistsInChat(principal.getUser().getLogin(), chatName)) {
            try {
                addUserInChat(userLogin, chatName);
            } catch (NoSuchElementException e) {
                throw new NoSuchElementException("User not Found");
            }
        } else throw new UserDoesNotHaveAccess("You does not have this chat");
    }

    @Override
    public void deleteUserFromChat(String userLogin, String chatName) {
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (checkUserPossibilitiesInChatService.checkUserConsistsInChat(principal.getUser().getLogin(), chatName)) {
            if (checkUserPossibilitiesInChatService.checkUserConsistsInChat(userLogin, chatName)) {
                User user = userRepository.findOneByLogin(userLogin).orElseThrow();
                Chat chat = chatRepository.findOneByName(chatName).orElseThrow();
                user.getJoinChats().remove(chat);
                userRepository.save(user);
            } else throw new ChatNotFoundException("In this chat User does not exist");
        } else throw new UserDoesNotHaveAccess("You does not have this chat");
    }

    private void addUserInChat(String userLogin, String chatName) {
        if (!checkUserPossibilitiesInChatService.checkUserConsistsInChat(userLogin, chatName)) {
            User user = userRepository.findOneByLogin(userLogin).orElseThrow();
            Chat chat = chatRepository.findOneByName(chatName).orElseThrow();
            if (user.getAge() >= chat.getAge()) {
                user.getJoinChats().add(chat);
                userRepository.save(user);
            } else throw new UserDoesNotHaveAccess("User too young");
        } else throw new ChatNotFoundException("In this chat User already exist");
    }


}
