package com.dp.deliveryservice.model;

import com.dp.deliveryservice.persistence.DeliveryMethod;

public record CalculateCostRequest(
        String countryCode,  // ????
        DeliveryMethod deliveryMethod,
        int weight,
        int price
) {
}
