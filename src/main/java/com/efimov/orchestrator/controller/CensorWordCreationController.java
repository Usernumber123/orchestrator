package com.efimov.orchestrator.controller;

import com.efimov.orchestrator.api.CensorApi;
import com.efimov.orchestrator.aspect.annotation.AspectLogger;
import com.efimov.orchestrator.model.WordDto;
import com.efimov.orchestrator.service.CensorWordService;
import com.efimov.orchestrator.service.ProducerForbiddenWordsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class CensorWordCreationController implements CensorApi {
    private final CensorWordService censorWordService;

    @Override
    @AspectLogger
    public ResponseEntity<Void> censorWordCreate(WordDto wordDto) {
        censorWordService.censorWordCreate(wordDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @AspectLogger
    public ResponseEntity<Void> censorWordDelete(WordDto wordDto) {
        censorWordService.censorWordDelete(wordDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
