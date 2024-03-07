package com.dp.orderservice.model;

public record CalculateCostResponse(
        double productPrice,
        double deliveryFee,
        double insuranceFee,
        double processingFee,
        double commission,
        double totalPriceUSA,
        double totalPriceUKR
) {
}
