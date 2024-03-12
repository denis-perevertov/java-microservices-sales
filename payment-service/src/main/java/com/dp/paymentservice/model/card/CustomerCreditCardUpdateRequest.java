package com.dp.paymentservice.model.card;

public record CustomerCreditCardUpdateRequest(
        String token,
        boolean isDefault
) {
}
