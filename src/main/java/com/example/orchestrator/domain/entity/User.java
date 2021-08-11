package com.example.orchestrator.domain.entity;

import lombok.*;


import javax.persistence.*;

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
