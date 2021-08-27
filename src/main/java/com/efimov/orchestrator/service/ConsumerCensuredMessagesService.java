package com.efimov.orchestrator.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface ConsumerCensuredMessagesService {
    void ListenCensuredMessageForSave(ConsumerRecord<String, String> messageReceived);
}
