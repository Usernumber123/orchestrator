package com.efimov.orchestrator.controller;

import com.efimov.orchestrator.api.MsgApi;
import com.efimov.orchestrator.aspect.annotation.AspectLogger;
import com.efimov.orchestrator.model.MessageDto;
import com.efimov.orchestrator.service.MessageReaderService;
import com.efimov.orchestrator.service.ProducerMessagesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@AspectLogger
public class MessageOrchestratorController implements MsgApi {
    private final MessageReaderService messageReaderService;
    private final ProducerMessagesService producerMessagesService;

    public MessageOrchestratorController(MessageReaderService messageReaderService, ProducerMessagesService producerMessagesService) {
        this.messageReaderService = messageReaderService;
        this.producerMessagesService = producerMessagesService;
    }

    @Override
    @AspectLogger
    public ResponseEntity<List<MessageDto>> getAllCacheMessagesFromChatOrFindByAuthor(String chat, String author) {

        return new ResponseEntity<>(messageReaderService.getAllMessagesFromChatOrFindByAuthor(chat, author), HttpStatus.OK);
    }

    @AspectLogger
    @Override
    public ResponseEntity<Void> sendToChatMessage(String chat, MessageDto messageDto) {
        producerMessagesService.sendMessageInChatAndCensured(chat, messageDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
