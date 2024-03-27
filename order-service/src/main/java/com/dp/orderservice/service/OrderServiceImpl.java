package com.dp.orderservice.service;

import com.dp.deliveryservice.persistence.DeliveryMethod;
import com.dp.orderservice.model.*;
import com.dp.orderservice.persistence.entity.*;
import com.dp.orderservice.persistence.repository.OrderItemRepository;
import com.dp.orderservice.persistence.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static com.dp.utils.EnumUtils.getEnum;
import static java.util.Objects.isNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    public List<Order> getAllOrders() {
        log.info("getting all orders");
        return orderRepository.findAll();
    }

    @Override
    public Page<Order> getOrdersPage(Specification<Order> spec, Pageable pageable) {
        Page<Order> page = orderRepository.findAll(spec, pageable);
        log.info("fetched orders (page {}/{})", page.getNumber(), page.getTotalPages());
        return page;
    }

    @Override
    public Order getOrder(Long id) {
        log.info("getting order (ID:{})", id);
        return orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Order not found!"));
    }

    @Override
    public Order saveOrder(Order order) {
        order = orderRepository.save(order);
        log.info("order saved (ID:{})", order);
        return order;
    }

    @Override
    @Transactional
    public Order createNewOrder(OrderCreateRequest request) {
        Order order = new Order();
        order
                .setUserId(request.userId())
                .setOrderNumber(UUID.randomUUID().toString())
                .setType(getEnum(request.orderType(), OrderType.class))
                .setStatus(Optional.ofNullable(getEnum(request.orderStatus(), OrderStatus.class)).orElse(OrderStatus.CALCULATING_COST));
        order = orderRepository.save(order);

        List<OrderItem> items = request.products()
                .stream()
                .map(product -> new OrderItem()
                    .setProductId(product.id())
                    .setQuantity(product.quantity())
                    .setExtraServices(product.extraServices().stream().map(str -> getEnum(str, ExtraService.class)).toList())
                    .setTrackingNumber(product.trackingNumber()))
                .toList();
        order.setItems(items);

        OrderPaymentInformation paymentInformation = new OrderPaymentInformation();
        order.setPaymentInformation(paymentInformation);

        OrderDeliveryInformation deliveryInformation = new OrderDeliveryInformation();
        deliveryInformation
                .setDeliveryMethod(getEnum(request.deliveryMethod(), DeliveryMethod.class))
                .setReceiverAddressId(request.receiverAddressId())
                .setWarehouseAddressId(request.warehouseAddressId());
        order.setDeliveryInformation(deliveryInformation);

        order = orderRepository.save(order);
        log.info("order created (ID: {})", order.getId());
        return order;
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
        log.info("deleted order (ID:{})", id);
    }

    @Override
    public void updateOrderStatus(OrderStatusUpdateRequest request) {
        if(isNull(request.id()) || isNull(request.status()) || request.status().isEmpty()) return;
        OrderStatus status = getEnum(request.status(), OrderStatus.class);
        if(!isNull(status)) {
            orderRepository.updateOrderStatus(request.id(), status);
            log.info("status updated for order (ID:{}) - new status is {}", request.id(), status);

            if(status.equals(OrderStatus.SENT_TO_RECEIVER_COUNTRY)) {
                orderRepository.updateOrderSentAt(request.id(), ZonedDateTime.now());
            }
            else if(status.equals(OrderStatus.DELIVERED_TO_RECEIVER)) {
                orderRepository.updateOrderDeliveredAt(request.id(), ZonedDateTime.now());
            }
        }

    }

    @Override
    public CalculateCostResponse calculateOrderCost(CalculateCostRequest request) {
        return null;
    }
}
