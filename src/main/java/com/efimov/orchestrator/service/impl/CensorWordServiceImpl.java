package com.efimov.orchestrator.service.impl;

import com.efimov.orchestrator.domain.entity.Chat;
import com.efimov.orchestrator.exceptions.ChatNotFoundException;
import com.efimov.orchestrator.exceptions.UserDoesNotHaveAccess;
import com.efimov.orchestrator.model.WordDto;
import com.efimov.orchestrator.repository.ChatRepository;
import com.efimov.orchestrator.security.UserDetailsImpl;
import com.efimov.orchestrator.service.CensorWordService;
import com.efimov.orchestrator.service.ProducerForbiddenWordsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class CensorWordServiceImpl implements CensorWordService {
    private final ChatRepository chatRepository;
    private final ProducerForbiddenWordsService producerForbiddenWordsService;

    @Override
    public void censorWordCreate(WordDto wordDto) {
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!wordDto.getChat().equals("*")) {
            try {
                Chat chat = chatRepository.findOneByName(wordDto.getChat()).orElseThrow();
                if (chat.getHost().equals(principal.getUser().getLogin())) {
                    producerForbiddenWordsService.registrationForbiddenWord(wordDto);
                } else throw new UserDoesNotHaveAccess("User is not the host");
            } catch (NoSuchElementException e) {
                throw new ChatNotFoundException("Chat does not exists");
            }
        } else throw new ChatNotFoundException("Chat does not exists");
    }

    @Override
    public void censorWordDelete(WordDto wordDto) {
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!wordDto.getChat().equals("*")) {
            try {
                Chat chat = chatRepository.findOneByName(wordDto.getChat()).orElseThrow();
                if (chat.getHost().equals(principal.getUser().getLogin())) {
                    producerForbiddenWordsService.deleteForbiddenWord(wordDto);
                } else throw new UserDoesNotHaveAccess("User is not the host");
            } catch (Exception e) {
                throw new ChatNotFoundException("Chat does not exists");
            }
        } else throw new ChatNotFoundException("Chat does not exists");
    }
}
