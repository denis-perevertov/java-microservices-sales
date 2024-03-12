package com.dp.userservice.model.user;

import java.time.ZonedDateTime;

public record UserPageRequest(
        Long id,
        String name,
        String email,
        String phone,
        ZonedDateTime beginDate,
        ZonedDateTime endDate,
        String gender,
        Double balanceFrom,
        Double balanceTo,
        Double totalTransactionSumFrom,
        Double totalTransactionSumTo,
        Integer page,
        Integer size
) {
    public UserPageRequest {
        page = page == null ? 0 : page;
        size = size == null ? 10 : size;
    }
}
