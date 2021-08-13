package com.example.orchestrator.controller;

import com.example.orchestrator.api.CensorApi;
import com.example.orchestrator.aspect.annotation.AspectLogger;
import com.example.orchestrator.model.WordDto;
import com.example.orchestrator.service.CensoredMsgService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class CensorWordController implements CensorApi {
    private final CensoredMsgService censoredMsgService;
    @Override
    @AspectLogger
    public ResponseEntity<Void> censorWord(WordDto wordDto) {
        censoredMsgService.sendWord(wordDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @Override
    @AspectLogger
    public ResponseEntity<Void> censorWordDelete(WordDto wordDto) {
        censoredMsgService.sendDeletedWord(wordDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
