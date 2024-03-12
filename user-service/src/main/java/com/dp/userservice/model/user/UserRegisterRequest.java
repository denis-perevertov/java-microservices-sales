package com.dp.userservice.model.user;

import java.util.List;

public record UserRegisterRequest(
        String username,
        String firstName,
        String lastName,
        Boolean enabled,
        String email,
        Boolean emailVerified,
        List<RegisterCredentials> credentials
) {
    public record RegisterCredentials(
            String type,
            Boolean temporary,
            String value
    ){}
}
