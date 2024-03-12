package com.dp.userservice.model.user;

import java.time.ZonedDateTime;

public record UserTransactionDTO (
        Long id,
        ZonedDateTime datetime,
        double sum,
        String type,
        String method
) {

}
