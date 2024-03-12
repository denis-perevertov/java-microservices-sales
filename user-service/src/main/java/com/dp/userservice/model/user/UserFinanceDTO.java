package com.dp.userservice.model.user;

import java.time.ZonedDateTime;
import java.util.List;

public record UserFinanceDTO(
        double balance,
        boolean automaticDebtDeduction,
        List<UserTransactionDTO> transactions
) {

}
