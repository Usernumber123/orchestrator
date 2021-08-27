package com.efimov.orchestrator.service;

import com.efimov.orchestrator.model.MessageDto;

public interface ProducerMessagesService {
    void sendMessageInChatAndCensured(String chatName, MessageDto messageDto);
}
