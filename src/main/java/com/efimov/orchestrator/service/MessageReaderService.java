package com.efimov.orchestrator.service;

import com.efimov.orchestrator.model.MessageDto;

import java.util.List;

public interface MessageReaderService {

    List<MessageDto> getAllMessagesFromChatOrFindByAuthor(String chat, String author);
}
