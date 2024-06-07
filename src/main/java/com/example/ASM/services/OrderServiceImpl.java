package com.example.ASM.services;

import com.example.ASM.models.Order;
import com.example.ASM.models.User;
import com.example.ASM.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Override
    public void addOrder(Order order) {
        orderRepository.save(order);
    }

    @Override
    public Order findById(UUID orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    @Override
    public List<Order> findAllByUser(User byUsername) {
        return orderRepository.findAllByUser(byUsername);
    }
}
