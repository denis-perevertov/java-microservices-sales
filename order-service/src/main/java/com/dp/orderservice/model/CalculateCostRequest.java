package com.dp.orderservice.model;

import com.dp.deliveryservice.persistence.DeliveryMethod;
import com.dp.utils.Country;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CalculateCostRequest(
        Country countryCode,
        @NotNull
        DeliveryMethod deliveryMethod,
        @NotEmpty
        List<Item> items
) {
    public record Item (
            @Min(1)
            int quantity,
            @Min(1)
            int weight,
            @Min(1)
            double price,
            List<String> extraServices
    ){}
}
