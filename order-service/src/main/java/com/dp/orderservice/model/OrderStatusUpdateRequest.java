package com.dp.orderservice.model;

public record OrderStatusUpdateRequest(
        Long id,
        String status
) {
}
