package com.dp.deliveryservice.model;

import com.dp.deliveryservice.persistence.DeliveryType;

public record CalculateCostRequest(
        String countryCode,  // ????
        DeliveryType deliveryType,
        int weight,
        int price
) {
}
