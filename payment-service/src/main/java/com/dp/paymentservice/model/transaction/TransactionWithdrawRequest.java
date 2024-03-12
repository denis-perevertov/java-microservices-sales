package com.dp.paymentservice.model.transaction;

public record TransactionWithdrawRequest (
        Long userId,
        double amount
) {
}
