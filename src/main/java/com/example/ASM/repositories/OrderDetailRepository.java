package com.example.ASM.repositories;

import com.example.ASM.models.Order;
import com.example.ASM.models.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, UUID> {

    @Query("SELECT COUNT(od) FROM OrderDetail od WHERE od.order.id = :orderId")
    int countByOrderId(@Param("orderId") UUID orderId);

    List<OrderDetail> findAllByOrderId(UUID id);
}
