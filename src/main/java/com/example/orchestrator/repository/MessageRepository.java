package com.example.orchestrator.repository;


import com.example.orchestrator.domain.entity.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends CrudRepository<Message, String> {
    List<Message> findAll();
}