package com.efimov.orchestrator.service;

public interface UsersInChatService {

    void addNewUsersInChat(String userLogin, String chatName);

    void deleteUserFromChat(String userLogin, String chatName);
}
