package com.dp.paymentservice.model.transaction;

public record UserTransactionRequest(
        String userId,
        double amount,
        TransactionType type
) {
    public enum TransactionType {
        ADD,
        WITHDRAW
    }
}
