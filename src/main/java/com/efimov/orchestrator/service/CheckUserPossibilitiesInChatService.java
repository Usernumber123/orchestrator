package com.efimov.orchestrator.service;


public interface CheckUserPossibilitiesInChatService {
    boolean checkUserConsistsInChat(String chatName, String userLogin);

    boolean checkUserOnHost(String userLogin, String chatName);
}
