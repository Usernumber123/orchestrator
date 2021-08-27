package com.efimov.orchestrator.service.impl;

import com.efimov.orchestrator.model.WordDto;
import com.efimov.orchestrator.service.ProducerForbiddenWordsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProducerForbiddenWordsServiceImpl implements ProducerForbiddenWordsService {
    public static final String REGISTRATION_FORBIDDEN_WORD_TOPIC = "registrationForbiddenWordsTopic";
    public static final String DELETE_FORBIDDEN_WORDS_TOPIC = "deleteForbiddenWordsTopic";
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void registrationForbiddenWord(WordDto wordDto) {
        kafkaTemplate.send(REGISTRATION_FORBIDDEN_WORD_TOPIC, wordDto);
        kafkaTemplate.flush();
    }

    public void deleteForbiddenWord(WordDto wordDto) {
        kafkaTemplate.send(DELETE_FORBIDDEN_WORDS_TOPIC, wordDto);
        kafkaTemplate.flush();
    }

}
