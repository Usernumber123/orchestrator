package com.efimov.orchestrator.controller;

import com.efimov.orchestrator.api.UserAtChatApi;
import com.efimov.orchestrator.service.UsersInChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class UsersInChatController implements UserAtChatApi {
    private final UsersInChatService usersInChatService;

    @Override
    public ResponseEntity<Void> addUserAtChat(String chat, String userLogin) {
        usersInChatService.addNewUsersInChat(userLogin, chat);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteUserFromChat(String chat, String userLogin) {
        usersInChatService.deleteUserFromChat(userLogin, chat);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
