package com.example.main.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import javax.persistence.Id;
import java.io.Serializable;

@RedisHash("Message")
@TypeAlias("Message")
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Message implements Serializable {
    @Id
    private String id;
    private String author;
    private String censoredMessage;
    private String dateAndTime;
    @TimeToLive
    private Long timeout=10L;

}