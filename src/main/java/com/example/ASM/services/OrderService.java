package com.example.ASM.services;

import com.example.ASM.models.Order;
import com.example.ASM.models.User;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    void addOrder(Order order);

    Order findById(UUID orderId);

    List<Order> findAllByUser(User byUsername);
}
