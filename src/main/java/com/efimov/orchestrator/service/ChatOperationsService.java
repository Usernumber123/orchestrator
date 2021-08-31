package com.efimov.orchestrator.service;

public interface ChatOperationsService {
    void deleteChat(String chat);

    void createChat(String chat, Integer age);

}
