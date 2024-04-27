package com.dp.userservice.model.user;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public record UserTransactionDTO (
        Long id,
        ZonedDateTime datetime,
        BigDecimal sum,
        String type,
        String method
) {

}
