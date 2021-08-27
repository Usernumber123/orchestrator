package com.efimov.orchestrator.service;

import com.efimov.orchestrator.model.WordDto;

public interface ProducerForbiddenWordsService {
    void registrationForbiddenWord(WordDto wordDto);

    void deleteForbiddenWord(WordDto wordDto);
}
