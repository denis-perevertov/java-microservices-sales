package com.dp.orderservice.service;

import com.dp.orderservice.model.CalculateCostRequest;
import com.dp.orderservice.model.CalculateCostResponse;
import com.dp.orderservice.model.OrderSaveRequest;
import com.dp.orderservice.persistence.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface OrderService {
    List<Order> getAllOrders();
    Page<Order> getOrdersPage(Pageable pageable, Specification<Order> spec);
    Order getOrder(Long id);
    Order saveOrder(Order order);
    Order saveOrder(OrderSaveRequest request);
    void deleteOrder(Long id);

    CalculateCostResponse calculateOrderCost(CalculateCostRequest request);
}
