package com.efimov.orchestrator.domain.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "login")})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class User {

    @Column(name = "login")
    private String login;
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @Column(name = "password")
    private String password;
    @Column(name = "age")
    private Integer age;
    @Column(name = "role")
    private String role = "USER";
    @ManyToMany
    @JoinTable(
            name = "users_chats_mtm",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "chat_id"))
    @EqualsAndHashCode.Exclude
    private Set<Chat> joinChats = new HashSet<>();

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", id='" + id + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", role='" + role + '\'';
    }

}
