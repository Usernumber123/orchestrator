package com.efimov.orchestrator.service.impl;

import com.efimov.orchestrator.domain.entity.User;
import com.efimov.orchestrator.exceptions.ChatNotFoundException;
import com.efimov.orchestrator.repository.MessageRepository;
import com.efimov.orchestrator.repository.UserRepository;
import com.efimov.orchestrator.security.UserDetailsImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MessageReaderServiceImplTest {
    MessageRepository messageRepository = mock(MessageRepository.class);
    private final UserRepository userRepository = mock(UserRepository.class);
    private final ConversionService conversionService = mock(ConversionService.class);
    private final CheckUserConsistsInChatImplService checkUserConsistsInChatImplService = mock(CheckUserConsistsInChatImplService.class) ;
    SecurityContext securityContext = Mockito.mock(SecurityContext.class);
    Authentication authentication = Mockito.mock(Authentication.class);
    MessageReaderServiceImpl messageService = new MessageReaderServiceImpl(messageRepository, conversionService, checkUserConsistsInChatImplService);



    @Test
    void getAllMessagesFromChat() {
        User user = new User();
        UserDetailsImpl principal = new UserDetailsImpl(user);
        Mockito.when(authentication.getPrincipal()).thenReturn((UserDetailsImpl) principal);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(userRepository.findOneByLogin(any())).thenReturn(java.util.Optional.of(user));
        assertThrows(ChatNotFoundException.class, () -> messageService.getAllMessagesFromChatOrFindByAuthor("chat", null));
    }
}