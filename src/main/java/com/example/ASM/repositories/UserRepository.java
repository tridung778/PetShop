package com.example.ASM.repositories;

import com.example.ASM.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    User findByUsername(String username);

    User findUserById(UUID id);

    User findByEmail(String email);

    User findByPhone(String phone);
}
