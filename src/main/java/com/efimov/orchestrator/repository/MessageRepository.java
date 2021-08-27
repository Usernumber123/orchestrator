package com.efimov.orchestrator.repository;


import com.efimov.orchestrator.domain.entity.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends CrudRepository<Message, String> {

    List<Message> findAll();

    List<Message> findByChat(String chat);

    List<Message> findByAuthorAndChat(String author, String chat);

}