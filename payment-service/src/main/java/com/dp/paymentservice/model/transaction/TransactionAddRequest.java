package com.dp.paymentservice.model.transaction;

public record TransactionAddRequest(
        String userId,
        double amount,
        boolean useDefaultMethod,
        String paymentMethodNonce
) {
}
