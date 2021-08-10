package com.example.main.service.impl;

import com.example.main.model.MessageDto;
import com.example.main.security.UserDetailsImpl;
import com.example.main.service.MessageService;
import com.example.main.domain.entity.Message;
import com.example.main.repository.MessageRepository;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.core.convert.ConversionService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import redis.clients.jedis.Jedis;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
@Slf4j
public class MessageServiceImpl implements MessageService {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final MessageRepository messageRepository;
    private final ConversionService conversionService;

    @Override
    public void sendMessage(MessageDto messageDto) {

        log.debug("Send MSG");
        setAuthor(messageDto);

        setDataTime(messageDto);

        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send("msg1", messageDto);
        kafkaTemplate.flush();
    }

    @Override
    public List<MessageDto> getAllMessages() {
        List<Message> messages = new ArrayList<>(messageRepository.findAll());
        log.info("FIND {}", messages);
        List<MessageDto> messageDtos = new ArrayList<>();
        for (Message message : messages)
            messageDtos.add(conversionService.convert(message, MessageDto.class));
        return messageDtos;
    }

    @KafkaListener(id = "asd", topics = "msg3")
    @Override
    public void receivedMessage(ConsumerRecord<String, String> messageReceived) {
        Message message = new Gson().fromJson(messageReceived.value(), Message.class);
        messageRepository.save(message);
    }

    private MessageDto setAuthor(MessageDto messageDto) {
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!principal.getAuthorities().toArray()[0].toString().equals("ADMIN")) {
            messageDto.setAuthor(principal.getUsername());
        }
        return messageDto;
    }

    private MessageDto setDataTime(MessageDto messageDto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        String strLocalDate =LocalDateTime.now().withNano(0).toString();

            LocalDateTime localDate = LocalDateTime.parse(strLocalDate, formatter);
            messageDto.setDateAndTime(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(localDate));
            return messageDto;

    }

}
