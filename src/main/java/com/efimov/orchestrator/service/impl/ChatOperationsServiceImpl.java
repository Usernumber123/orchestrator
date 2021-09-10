package com.efimov.orchestrator.service.impl;

import com.efimov.orchestrator.domain.entity.Chat;
import com.efimov.orchestrator.domain.entity.User;
import com.efimov.orchestrator.exceptions.UserDoesNotHaveAccess;
import com.efimov.orchestrator.repository.ChatRepository;
import com.efimov.orchestrator.repository.UserRepository;
import com.efimov.orchestrator.security.UserDetailsImpl;
import com.efimov.orchestrator.service.ChatOperationsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;


@Service
@RequiredArgsConstructor
public class ChatOperationsServiceImpl implements ChatOperationsService {
    private final UserRepository userRepository;
    private final ChatRepository chatRepository;

    @Override
    public void deleteChat(String chat){
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            Chat chat1 = chatRepository.findOneByName(chat).orElseThrow();
            if (chat1.getHost().equals(principal.getUser().getLogin())) {
                chatRepository.deleteChatByName(chat);
            } else throw new UserDoesNotHaveAccess("User is not the host of this chat");
        }catch (NoSuchElementException e) {throw new NoSuchElementException("Chat not Found");}
    }
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

        User user = userRepository.findOneByLogin(principal.getUser().getLogin()).orElseThrow();
        Chat chat2 = chatRepository.findOneByName(chat).orElseThrow();
        if (user.getAge() >= chat2.getAge()) {
            user.getJoinChats().add(chat2);
            userRepository.save(user);
        } else {
            chatRepository.delete(chat1);
            throw new UserDoesNotHaveAccess("User too young");
        }
    }

}
