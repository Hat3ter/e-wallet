package com.kuehne.nagel.ewalletapi.repositories;

import com.kuehne.nagel.ewalletapi.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    User findByLogin(String login);
}
