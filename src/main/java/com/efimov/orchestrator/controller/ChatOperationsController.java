package com.efimov.orchestrator.controller;

import com.efimov.orchestrator.api.AddUserAtChatApi;
import com.efimov.orchestrator.api.CreateChatApi;
import com.efimov.orchestrator.service.ChatOperationsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Optional;


@RestController
@Slf4j
@RequiredArgsConstructor
public class ChatOperationsController implements CreateChatApi, AddUserAtChatApi {
    private final ChatOperationsService chatOperationsService;

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return CreateChatApi.super.getRequest();
    }

    @Override
    public ResponseEntity<Void> createChat(String chat, Integer age) {
        chatOperationsService.createChat(chat, age);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Override
    public ResponseEntity<Void> addUserAtChat(String chat, String userLogin) {
        chatOperationsService.addNewUsersInChat(userLogin, chat);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
