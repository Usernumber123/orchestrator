package com.efimov.orchestrator.repository;

import com.efimov.orchestrator.domain.entity.Chat;
import com.efimov.orchestrator.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    Optional<Chat> findOneByName(String name);
    @Transactional
    void deleteChatByName(String name);
}
