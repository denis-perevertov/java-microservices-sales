package com.dp.orderservice.model;

import java.time.ZonedDateTime;
import java.util.List;

public record OrderDTO(
        Long id,
        String orderNumber,
        OrderUserDTO user,
        List<OrderItemDTO> items,
        OrderPaymentInfoDTO paymentInformation,
        OrderDeliveryInfoDTO deliveryInformation,
        String status,
        String type,
        ZonedDateTime createdAt
) {

}
