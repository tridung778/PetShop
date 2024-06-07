package com.example.ASM.services;

import com.example.ASM.dto.CartDto;
import com.example.ASM.models.User;
import com.example.ASM.repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Override
    public Optional<CartDto> findById(UUID id) {
        return cartRepository.findById(id);
    }

    @Override
    public CartDto save(CartDto cartDto) {
        return cartRepository.save(cartDto);
    }

    @Override
    public List<CartDto> findAll() {
        return cartRepository.findAll();
    }

    @Override
    public List<CartDto> findAllByUser(User user) {
        return cartRepository.findAllByUser(user);
    }

    @Override
    @Transactional
    public void removeAllByUser(User user) {
        cartRepository.deleteByUser(user);
    }

    @Override
    public void deleteById(UUID id) {
        cartRepository.deleteById(id);
    }
}
