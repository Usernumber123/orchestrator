package com.example.main.service;

import com.example.main.model.WordDto;

public interface CensoredMsgService {
    void sendWord(WordDto msg);
    void sendDeletedWord(WordDto msg);
}
