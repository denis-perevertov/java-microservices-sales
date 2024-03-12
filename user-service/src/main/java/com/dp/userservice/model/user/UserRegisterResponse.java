package com.dp.userservice.model.user;

public record UserRegisterResponse(
        int status,
        String authId,
        Object errors
) {
}
