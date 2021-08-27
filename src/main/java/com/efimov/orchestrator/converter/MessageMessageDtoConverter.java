package com.efimov.orchestrator.converter;

import com.efimov.orchestrator.model.MessageDto;
import com.efimov.orchestrator.domain.entity.Message;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MessageMessageDtoConverter implements Converter<Message, MessageDto> {


    @Override
    public MessageDto convert(Message message) {
        MessageDto messageDto = new MessageDto();
        messageDto.setMessage(message.getCensoredMessage());
        messageDto.setAge(message.getAge());
        messageDto.setDateAndTime(message.getDateAndTime());
        messageDto.setAuthor(message.getAuthor());
        messageDto.setChat(message.getChat());
        return messageDto;
    }
}
