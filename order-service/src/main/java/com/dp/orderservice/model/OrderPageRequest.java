package com.dp.orderservice.model;

import java.time.ZonedDateTime;

public record OrderPageRequest(
        Long id,
        String orderNumber,
        Long userId,
        Double priceFrom,
        Double priceTo,
        String deliveryMethod,
        String trackingNumber,
        ZonedDateTime deliveredFrom,
        ZonedDateTime deliveredTo,
        String status,
        String type,
        ZonedDateTime createdFrom,
        ZonedDateTime createdTo,
        Integer page,
        Integer size
) {
    public OrderPageRequest {
        page = page == null ? 0 : page;
        size = size == null ? 10 : size;
    }
}
