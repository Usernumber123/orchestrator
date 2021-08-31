package com.efimov.orchestrator.controller;

import com.efimov.orchestrator.api.ChatApi;
import com.efimov.orchestrator.service.ChatOperationsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;



@RestController
@Slf4j
@RequiredArgsConstructor
public class ChatOperationsController implements ChatApi {
    private final ChatOperationsService chatOperationsService;


    @Override
    public ResponseEntity<Void> createChat(String chat, Integer age) {
        chatOperationsService.createChat(chat, age);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteChat(String chat) {
        chatOperationsService.deleteChat(chat);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
