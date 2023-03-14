package com.kuehne.nagel.ewalletapi.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Table(name = "users")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    /**
     * Id
     */
    @Id
    @Column(name = "id", columnDefinition = "uuid")
    @GeneratedValue
    private UUID id;

    /**
     * email
     */
    @Column(name = "login")
    private String login;

    /**
     * password encrypted
     */
    @Column(name = "password")
    private String password;

    /**
     * role
     */
    @Column(name = "role")
    private String role;



}
