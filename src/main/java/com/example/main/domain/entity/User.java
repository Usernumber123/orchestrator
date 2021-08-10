package com.example.main.domain.entity;

import lombok.*;
import org.springframework.stereotype.Component;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

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
    @JoinColumn(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "password")
    private String password;
    @Column(name = "role")
    private String Role="USER";

}
