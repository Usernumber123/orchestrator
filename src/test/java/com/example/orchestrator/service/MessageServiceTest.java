package com.example.orchestrator.service;


import com.example.orchestrator.domain.entity.Message;
import com.example.orchestrator.model.MessageDto;
import com.example.orchestrator.repository.MessageRepository;
import com.example.orchestrator.service.impl.MessageServiceImpl;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.ConversionService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
/*
class MessageServiceTest {
MessageRepository messageRepository=mock(MessageRepository.class);
    private final KafkaTemplate<String, Object> kafkaTemplate=mock(KafkaTemplate.class);
    private final ConversionService conversionService=mock(ConversionService.class);
    private final SecurityContextHolder securityContextHolder=mock(SecurityContextHolder.class);
    ConsumerRecord<String, String> messageReceived=new ConsumerRecord<>("1",1,1,""," {\n" +
            "\"author\" : \"Maina\",\n" +
            "\"message\" : \"UnCensured sss\"\n" +
            " }");
    MessageServiceImpl messageService =new MessageServiceImpl(kafkaTemplate,messageRepository,conversionService);

    @Test
    void receivedMessage() {
        messageService.receivedMessage(messageReceived);
        verify(messageRepository).save(any());
    }


    @Test
    void getAllMessages() {
        List<Message> listMessages=new ArrayList<>();
        listMessages.add(new Message());
        when(messageRepository.findAll()).thenReturn(listMessages);
        messageService.getAllMessages();
        verify(messageRepository).findAll();
        verify(conversionService).convert(any(),eq(MessageDto.class));
    }
}*/