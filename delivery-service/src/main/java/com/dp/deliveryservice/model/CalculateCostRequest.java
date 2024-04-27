package com.dp.deliveryservice.model;

import com.dp.deliveryservice.persistence.DeliveryMethod;
import com.dp.utils.Country;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CalculateCostRequest(
        Country countryCode,
        @NotNull
        DeliveryMethod deliveryMethod,
        @Min(0)
        int weight,
        @Min(0)
        double price
) {
}
