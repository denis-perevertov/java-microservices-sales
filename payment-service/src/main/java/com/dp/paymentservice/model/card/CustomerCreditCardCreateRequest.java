package com.dp.paymentservice.model.card;

public record CustomerCreditCardCreateRequest(
        String userId,
        String paymentMethodNonce,
        boolean setAsDefault
) {
}
