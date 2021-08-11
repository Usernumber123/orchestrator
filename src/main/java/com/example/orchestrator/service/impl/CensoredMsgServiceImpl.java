package com.example.orchestrator.service.impl;

import com.example.orchestrator.model.WordDto;
import com.example.orchestrator.security.UserDetailsImpl;
import com.example.orchestrator.service.CensoredMsgService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
@Service
@Slf4j
@RequiredArgsConstructor
public class CensoredMsgServiceImpl implements CensoredMsgService {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    public void sendWord(WordDto wordDto) {
        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send("forbiddenWords", wordDto);
        kafkaTemplate.flush();
    }
    public void sendDeletedWord(WordDto wordDto) {
        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send("deleteForbiddenWords", wordDto);
        kafkaTemplate.flush();
    }

}
