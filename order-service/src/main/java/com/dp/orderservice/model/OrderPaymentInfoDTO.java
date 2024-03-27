package com.dp.orderservice.model;

public record OrderPaymentInfoDTO(
        double productPrice,
        double commissionFee,
        double insuranceFee,
        double processingFee,
        double deliveryFee,
        double approximateWeight,
        double realWeight,
        double totalPrice
) {
}
