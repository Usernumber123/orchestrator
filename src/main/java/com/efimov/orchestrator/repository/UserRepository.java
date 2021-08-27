package com.efimov.orchestrator.repository;

import com.efimov.orchestrator.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findOneByLogin(String login);

    User getById(String s);

    void deleteById(String s);
}
