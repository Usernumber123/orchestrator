package com.example.main.converter;

import com.example.main.model.MessageDto;
import com.example.main.domain.entity.Message;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MessageMessageDtoConverter implements Converter<Message, MessageDto> {


    @Override
    public MessageDto convert(Message message) {
        MessageDto messageDto = new MessageDto();
        messageDto.setMessage(message.getCensoredMessage());
        messageDto.setDateAndTime(message.getDateAndTime());
        messageDto.setAuthor(message.getAuthor());
        return messageDto;
    }
}
