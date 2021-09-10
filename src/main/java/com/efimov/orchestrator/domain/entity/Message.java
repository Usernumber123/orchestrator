package com.efimov.orchestrator.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.Id;
import java.io.Serializable;

@RedisHash("Message")
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Message implements Serializable{
    @Id
    private String id;
    @Indexed
    private String author;
    private Integer age;
    private String censoredMessage;
    private String dateAndTime;
    @Indexed
    private String chat;
    @TimeToLive
    private Long timeout = 1000L;

}