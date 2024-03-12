package com.dp.userservice.model.user;

public record UserDTO (
        UserProfileDTO profile,
        UserFinanceDTO financeDetails
) {
}
