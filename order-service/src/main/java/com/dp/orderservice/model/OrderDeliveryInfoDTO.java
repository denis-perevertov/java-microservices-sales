package com.dp.orderservice.model;

import java.time.ZonedDateTime;

public record OrderDeliveryInfoDTO(
        Long id,
        String deliveryMethod,
        String trackingNumber,
        ZonedDateTime sentAt,
        ZonedDateTime deliveredAt
) {
}
