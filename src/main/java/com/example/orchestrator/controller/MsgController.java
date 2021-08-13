package com.example.orchestrator.controller;

import com.example.orchestrator.api.MsgApi;
import com.example.orchestrator.aspect.annotation.AspectLogger;
import com.example.orchestrator.model.MessageDto;
import com.example.orchestrator.service.impl.MessageServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@AspectLogger
public class MsgController implements MsgApi {
    private final MessageServiceImpl messageService;

    public MsgController(MessageServiceImpl messageService) {
        this.messageService = messageService;
    }
    @Override
    @AspectLogger
    public ResponseEntity<List<MessageDto>> getAllCacheMessages() {

        return new ResponseEntity<>( messageService.getAllMessages(),HttpStatus.OK);
    }

    @Override
    @AspectLogger
    public ResponseEntity<Void> sendMessage(MessageDto messageDto) {
        messageService.sendMessage( messageDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
