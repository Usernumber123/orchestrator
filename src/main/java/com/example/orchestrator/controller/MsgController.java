package com.example.orchestrator.controller;

import com.example.orchestrator.api.MsgApi;
import com.example.orchestrator.model.MessageDto;
import com.example.orchestrator.service.impl.MessageServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@Slf4j
public class MsgController implements MsgApi {
    private final MessageServiceImpl messageService;

    public MsgController(MessageServiceImpl messageService) {
        this.messageService = messageService;
    }

    @Override
    public ResponseEntity<List<MessageDto>> getAllCacheMessages() {
        log.debug("GET ALL CONTROLLER");
        return new ResponseEntity<>( messageService.getAllMessages(),HttpStatus.OK);
    }


    @Override
    public ResponseEntity<Void> sendMessage(MessageDto messageDto) {
        messageService.sendMessage(messageDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
