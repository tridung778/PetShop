package com.example.ASM.repositories;

import com.example.ASM.models.Order;
import com.example.ASM.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findAllByUser(User byUsername);
}
