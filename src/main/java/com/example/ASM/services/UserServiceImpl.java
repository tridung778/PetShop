package com.example.ASM.services;


import com.example.ASM.models.User;
import com.example.ASM.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void register(User user) {
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String userName) {
        return userRepository.findByUsername(userName);
    }

    @Override
    public User findUserById(UUID id) {
        return userRepository.findUserById(id);
    }

    @Override
    public boolean isDuplicateUser(User user) {
        return findByUsername(user.getUsername()) != null || userRepository.findByEmail(user.getEmail()) != null;
    }

    @Override
    public User findById(UUID userId) {
        return userRepository.getReferenceById(userId);
    }

}
