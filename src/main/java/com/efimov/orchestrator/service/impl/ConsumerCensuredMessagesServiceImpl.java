package com.efimov.orchestrator.service.impl;

import com.efimov.orchestrator.aspect.annotation.AspectLogger;
import com.efimov.orchestrator.domain.entity.Message;
import com.efimov.orchestrator.repository.MessageRepository;
import com.efimov.orchestrator.service.ConsumerCensuredMessagesService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConsumerCensuredMessagesServiceImpl implements ConsumerCensuredMessagesService {
    public static final String MESSAGE_FOR_STORE_TOPIC = "MessageForStoreTopic";
    public static final String LISTENER_ID_6 = "listenerId6";
    private final MessageRepository messageRepository;

    @AspectLogger
    @KafkaListener(id = LISTENER_ID_6, topics = MESSAGE_FOR_STORE_TOPIC)
    public void ListenCensuredMessageForSave(ConsumerRecord<String, String> messageReceived) {
        Message message = new Gson().fromJson(messageReceived.value(), Message.class);
        messageRepository.save(message);
    }
}
