package com.dp.deliveryservice.model;

public record CalculateCostResponse(
        double deliveryFee,
        double insuranceFee,
        double processingFee,
        double commission,
        double totalPrice
) {
}
