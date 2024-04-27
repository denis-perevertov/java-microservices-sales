package com.dp.userservice.model.user;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

public record UserFinanceDTO(
        BigDecimal balance,
        boolean automaticDebtDeduction,
        List<UserTransactionDTO> transactions
) {

}
