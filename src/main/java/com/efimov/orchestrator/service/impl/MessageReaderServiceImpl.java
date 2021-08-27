package com.efimov.orchestrator.service.impl;

import com.efimov.orchestrator.domain.entity.Chat;
import com.efimov.orchestrator.domain.entity.User;
import com.efimov.orchestrator.exceptions.ChatNotFoundException;
import com.efimov.orchestrator.model.MessageDto;
import com.efimov.orchestrator.repository.UserRepository;
import com.efimov.orchestrator.security.UserDetailsImpl;
import com.efimov.orchestrator.service.MessageReaderService;
import com.efimov.orchestrator.domain.entity.Message;
import com.efimov.orchestrator.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
@RequiredArgsConstructor
public class MessageReaderServiceImpl implements MessageReaderService {
    private final MessageRepository messageRepository;
    private final ConversionService conversionService;
    private final UserRepository userRepository;

    @Override
    public List<MessageDto> getAllMessagesFromChatOrFindByAuthor(String chat, String author) {
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findOneByLogin(principal.getUser().getLogin()).orElseThrow();
        if (!user.getJoinChats().isEmpty()) {
            for (Chat chat1 : new ArrayList<>(user.getJoinChats())) {
                if (chat1.getName().equals(chat)) {
                    List<Message> messages = findParams(chat, author);
                    boolean flag = messages.isEmpty();
                    messages.removeAll(Collections.singleton(null));
                    if (!flag)
                        if (messages.isEmpty())
                            messageRepository.deleteAll();
                    List<MessageDto> messageDtos = new ArrayList<>();
                    for (Message message : messages)
                        messageDtos.add(conversionService.convert(message, MessageDto.class));
                    return messageDtos;
                }
            }
        }
        throw new ChatNotFoundException("User does not have this chat");
    }

    private List<Message> findParams(String chat, String author) {
        List<Message> messages;
        if (author == null) {
            messages = new ArrayList<>(messageRepository.findByChat(chat));
        } else messages = new ArrayList<>(messageRepository.findByAuthorAndChat(author, chat));
        return messages;
    }

}
