package com.dp.paymentservice.model.transaction;

import java.math.BigDecimal;

public record TransactionAddRequest(
        String userId,
        BigDecimal amount,
        boolean useDefaultMethod,
        String paymentMethodNonce
) {
}
