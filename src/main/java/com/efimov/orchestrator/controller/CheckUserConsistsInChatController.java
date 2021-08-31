package com.efimov.orchestrator.controller;

import com.efimov.orchestrator.api.CheckApi;
import com.efimov.orchestrator.aspect.annotation.AspectLogger;
import com.efimov.orchestrator.service.impl.CheckUserConsistsInChatImplService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AspectLogger
@RequiredArgsConstructor
public class CheckUserConsistsInChatController implements CheckApi {
    private final CheckUserConsistsInChatImplService checkUserConsistsInChatImplService;
    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    public ResponseEntity<Void> checkUserConsistsInChat(String chatName, String userLogin) {
        if(checkUserConsistsInChatImplService.checkUserConsistsInChatBool( userLogin,chatName))return new ResponseEntity<>(HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
