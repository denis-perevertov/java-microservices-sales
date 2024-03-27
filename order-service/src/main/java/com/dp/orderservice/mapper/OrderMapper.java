package com.dp.orderservice.mapper;

import com.dp.deliveryservice.persistence.DeliveryMethod;
import com.dp.orderservice.model.OrderCreateRequest;
import com.dp.orderservice.model.OrderDTO;
import com.dp.orderservice.model.OrderItemDTO;
import com.dp.orderservice.model.OrderItemProductDTO;
import com.dp.orderservice.persistence.entity.Order;
import com.dp.orderservice.persistence.entity.OrderItem;
import com.dp.orderservice.persistence.entity.OrderStatus;
import com.dp.orderservice.persistence.entity.OrderType;
import com.dp.orderservice.persistence.repository.OrderRepository;
import com.dp.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.dp.utils.EnumUtils.getEnum;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final OrderService orderService;
    private final OrderRepository orderRepository;

    public OrderDTO orderToDTO(Order order) {
        return null;
    }

    public OrderItemDTO orderItemToDTO(OrderItem orderItem,
                                       OrderItemProductDTO product) {
        return new OrderItemDTO(
                orderItem.getId(),
                product,
                orderItem.getQuantity(),
                orderItem.getExtraServices().stream().map(Enum::name).toList(),
                orderItem.getTrackingNumber(),
                orderItem.getComment()
        );
    }

    private Order mapOrder(Order order, OrderCreateRequest request) {
        order
                .setUserId(request.userId())
                .setType(getEnum(request.orderType(), OrderType.class))
                .setStatus(getEnum(request.orderStatus(), OrderStatus.class));
        // payment information is unchanged ?
        order.getDeliveryInformation()
                .setDeliveryMethod(getEnum(request.deliveryMethod(), DeliveryMethod.class))
                .setReceiverAddressId(request.receiverAddressId())
                .setWarehouseAddressId(request.warehouseAddressId());
        return order;
    }
}
