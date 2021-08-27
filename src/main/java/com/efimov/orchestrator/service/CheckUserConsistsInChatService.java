package com.efimov.orchestrator.service;

import org.springframework.http.ResponseEntity;

public interface CheckUserConsistsInChatService {
    ResponseEntity<Void> checkUserConsistsInChat(String chatName, String userLogin);
}
