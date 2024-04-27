package com.dp.paymentservice.model;

import java.math.BigDecimal;

public record UserBalanceUpdateRequest(
        Long userId,
        BigDecimal amount,
        UpdateType type
) {
    public enum UpdateType {
        ADD,
        SUBTRACT
    }
}
