package com.efimov.orchestrator.service;

import com.efimov.orchestrator.model.WordDto;

public interface CensorWordService {
    void censorWordCreate(WordDto wordDto);

    void censorWordDelete(WordDto wordDto);
}
