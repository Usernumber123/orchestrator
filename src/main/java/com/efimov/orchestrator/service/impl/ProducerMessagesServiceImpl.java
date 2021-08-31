package com.efimov.orchestrator.service.impl;

import com.efimov.orchestrator.domain.entity.Chat;
import com.efimov.orchestrator.domain.entity.User;
import com.efimov.orchestrator.exceptions.ChatNotFoundException;
import com.efimov.orchestrator.model.MessageDto;
import com.efimov.orchestrator.repository.UserRepository;
import com.efimov.orchestrator.security.UserDetailsImpl;
import com.efimov.orchestrator.service.ProducerMessagesService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProducerMessagesServiceImpl implements ProducerMessagesService {
    public static final String MESSAGE_FOR_CENSURED_TOPIC = "messageForCensuredTopic";
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final UserRepository userRepository;
    private final CheckUserConsistsInChatImplService checkUserConsistsInChatImplService;

    @Override
    public void sendMessageInChatAndCensured(String chatName, MessageDto messageDto) {

        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (checkUserConsistsInChatImplService.checkUserConsistsInChatBool(principal.getUser().getLogin(), chatName)) {
            messageDto.setChat(chatName);
            setAuthor(messageDto);
            setAge(messageDto);
            setDataTime(messageDto);

            kafkaTemplate.send(MESSAGE_FOR_CENSURED_TOPIC, messageDto);
            kafkaTemplate.flush();
        } else
            throw new ChatNotFoundException("User does not have this chat");
    }


    private void setAuthor(MessageDto messageDto) {
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        messageDto.setAuthor(principal.getUsername());
    }


    private void setDataTime(MessageDto messageDto) {
        String strLocalDate = LocalDateTime.now().toString();
        messageDto.setDateAndTime(strLocalDate.replace("T", " "));
    }

    private void setAge(MessageDto messageDto) {
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        messageDto.age(principal.getUser().getAge());
    }
}
