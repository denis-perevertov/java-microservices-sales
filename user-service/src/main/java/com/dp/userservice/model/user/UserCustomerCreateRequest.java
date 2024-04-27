package com.dp.userservice.model.user;

public record UserCustomerCreateRequest(
        String userId,
        String firstName,
        String lastName,
        String email,
        String phone
) {
}
