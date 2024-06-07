package com.example.ASM.services;

import com.example.ASM.models.OrderDetail;

import java.util.List;
import java.util.UUID;

public interface OrderDetailService {
    void addOrderDetail(OrderDetail orderDetail);

    int getQuantityByOrderId(UUID orderId);

    List<OrderDetail> findAllByOrderId(UUID id);
}
