package com.dp.orderservice.persistence.repository;

import com.dp.orderservice.persistence.entity.Order;
import com.dp.orderservice.persistence.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.ZonedDateTime;

public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    @Modifying
    @Query("""
            UPDATE Order o
            SET o.status = :status
            WHERE o.id = :orderId
            """)
    void updateOrderStatus(Long orderId, OrderStatus status);

    @Modifying
    @Query("""
            UPDATE Order o
            SET o.deliveryInformation.sentAt = :datetime
            WHERE o.id = :orderId
            """)
    void updateOrderSentAt(Long orderId, ZonedDateTime datetime);

    @Modifying
    @Query("""
            UPDATE Order o
            SET o.deliveryInformation.deliveredAt = :datetime
            WHERE o.id = :orderId
            """)
    void updateOrderDeliveredAt(Long orderId, ZonedDateTime datetime);
}
