package com.example.ASM.services;


import com.example.ASM.models.OrderDetail;
import com.example.ASM.repositories.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderDetailImpl implements OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    public void addOrderDetail(OrderDetail orderDetail) {
        orderDetailRepository.save(orderDetail);
    }

    @Override
    public int getQuantityByOrderId(UUID orderId) {
        return orderDetailRepository.countByOrderId(orderId);
    }

    @Override
    public List<OrderDetail> findAllByOrderId(UUID id) {
        return orderDetailRepository.findAllByOrderId(id);
    }
}
