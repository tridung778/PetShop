package com.example.ASM.services;

import com.example.ASM.models.User;

import java.util.Optional;
import java.util.UUID;

public interface UserService {
    void register(User user);

    User findByUsername(String userName);

    User findUserById(UUID id);

    boolean isDuplicateUser(User user);

    User findById(UUID userId);
}
