package com.efimov.orchestrator.service.impl;

import com.efimov.orchestrator.exceptions.ChatNotFoundException;
import com.efimov.orchestrator.model.MessageDto;
import com.efimov.orchestrator.security.UserDetailsImpl;
import com.efimov.orchestrator.service.MessageReaderService;
import com.efimov.orchestrator.domain.entity.Message;
import com.efimov.orchestrator.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class MessageReaderServiceImpl implements MessageReaderService {
    private final MessageRepository messageRepository;
    private final ConversionService conversionService;
    private final CheckUserConsistsInChatImplService checkUserConsistsInChatImplService;


    @Override
    public List<MessageDto> getAllMessagesFromChatOrFindByAuthor(String chat, String author) {
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (checkUserConsistsInChatImplService.checkUserConsistsInChatBool(principal.getUser().getLogin(), chat)) {
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
        } else
            throw new ChatNotFoundException("User does not have this chat");
    }

    private void sortingMessagesByDate(List<Message> messages) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Comparator<Message> descender = (o2, o1) -> {
            Date date = null;
            Date date1 = null;
            try {
                date = formatter.parse(o1.getDateAndTime());
                date1 = formatter.parse(o2.getDateAndTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return Objects.requireNonNull(date1).compareTo(date);
        };
        messages.sort(descender);
    }

    private List<Message> findParams(String chat, String author) {
        List<Message> messages;
        if (author == null) {
            messages = new ArrayList<>(messageRepository.findByChat(chat));
        } else messages = new ArrayList<>(messageRepository.findByAuthorAndChat(author, chat));
        sortingMessagesByDate(messages);
        return messages;
    }

}
