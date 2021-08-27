package com.efimov.orchestrator.service;

public interface ChatOperationsService {
    void createChat(String chat, Integer age);

    void addNewUsersInChat(String userLogin, String chatName);
}
