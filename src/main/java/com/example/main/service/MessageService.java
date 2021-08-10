package com.example.main.service;

import com.example.main.model.MessageDto;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.List;

public interface MessageService {
    void sendMessage(MessageDto msg);
    List<MessageDto> getAllMessages();

    void receivedMessage(ConsumerRecord<String, String> messageReceived);
}
