package com.example.orchestrator.service;

import com.example.orchestrator.model.WordDto;

public interface CensoredMsgService {
    void sendWord(WordDto msg);
    void sendDeletedWord(WordDto msg);
}
