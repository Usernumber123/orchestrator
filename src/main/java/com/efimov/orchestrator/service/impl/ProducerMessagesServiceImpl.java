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
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ProducerMessagesServiceImpl implements ProducerMessagesService {
    public static final String MESSAGE_FOR_CENSURED_TOPIC = "messageForCensuredTopic";
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final UserRepository userRepository;

    @Override
    public void sendMessageInChatAndCensured(String chatName, MessageDto messageDto) {

        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findOneByLogin(principal.getUser().getLogin()).orElseThrow();
        boolean flag=true;
        if (!user.getJoinChats().isEmpty()) {
            for (Chat chat : new ArrayList<>(user.getJoinChats())) {
                if (chat.getName().equals(chatName)) {
                    messageDto.setChat(chatName);
                    setAuthor(messageDto);
                    setAge(messageDto);
                    setDataTime(messageDto);
                    flag=false;
                    kafkaTemplate.send(MESSAGE_FOR_CENSURED_TOPIC, messageDto);
                    kafkaTemplate.flush();
                }
            }

        }
        if(flag)
        throw new ChatNotFoundException("User does not have this chat");
    }


    private void setAuthor(MessageDto messageDto) {
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        messageDto.setAuthor(principal.getUsername());
    }


    private void setDataTime(MessageDto messageDto) {
        String strLocalDate = LocalDateTime.now().withNano(0).toString();
        messageDto.setDateAndTime(strLocalDate.replace("T", " "));
    }

    private void setAge(MessageDto messageDto) {
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        messageDto.age(principal.getUser().getAge());
    }
}
