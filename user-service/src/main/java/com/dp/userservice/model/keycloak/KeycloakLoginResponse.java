package com.dp.userservice.model.keycloak;

import com.fasterxml.jackson.annotation.JsonProperty;

public record KeycloakLoginResponse(
        @JsonProperty("access_token")
        String accessToken,
        @JsonProperty("refresh_token")
        String refreshToken
) {
}
