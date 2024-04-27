package com.dp.paymentservice.model.transaction;

import java.math.BigDecimal;

public record TransactionWithdrawRequest (
        Long userId,
        BigDecimal amount
) {
}
