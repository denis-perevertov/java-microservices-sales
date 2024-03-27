package com.dp.orderservice.model;

import java.util.List;

public record OrderItemDTO(
        Long id,
        OrderItemProductDTO product,
        int quantity,
        List<String> extraServices,
        String trackingNumber,
        String comment
) {
}
