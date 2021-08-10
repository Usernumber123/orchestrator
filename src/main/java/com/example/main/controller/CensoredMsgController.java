package com.example.main.controller;

import com.example.main.api.CensorApi;
import com.example.main.model.WordDto;
import com.example.main.service.CensoredMsgService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class CensoredMsgController implements CensorApi {
    private final CensoredMsgService censoredMsgService;
    @Override
    public ResponseEntity<Void> censorWord(WordDto wordDto) {
        censoredMsgService.sendWord(wordDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> censorWordDelete(WordDto wordDto) {
        censoredMsgService.sendDeletedWord(wordDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
