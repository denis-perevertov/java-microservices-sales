package com.dp.orderservice.service;

import com.dp.orderservice.model.*;
import com.dp.orderservice.persistence.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface OrderService {
    List<Order> getAllOrders();
    Page<Order> getOrdersPage(Specification<Order> spec, Pageable pageable);
    Order getOrder(Long id);
    Order saveOrder(Order order);
    Order createNewOrder(OrderCreateRequest request);
    void deleteOrder(Long id);

    void updateOrderStatus(OrderStatusUpdateRequest request);

    CalculateCostResponse calculateOrderCost(CalculateCostRequest request);
}
