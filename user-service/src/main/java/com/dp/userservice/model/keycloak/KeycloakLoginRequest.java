package com.dp.userservice.model.keycloak;

public record KeycloakLoginRequest(
        String clientId,
        String clientSecret,
        String username,
        String password,
        String grantType
) {
}
