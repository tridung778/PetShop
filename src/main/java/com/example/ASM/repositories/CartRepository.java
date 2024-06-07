package com.example.ASM.repositories;

import com.example.ASM.dto.CartDto;
import com.example.ASM.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<CartDto, UUID> {

    List<CartDto> findAllByUser(User user);

    void deleteByUser(User user);
}
