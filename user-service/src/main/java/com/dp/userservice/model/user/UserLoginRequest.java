package com.dp.userservice.model.user;

public record UserLoginRequest(
        String username,
        String password
) {
}
