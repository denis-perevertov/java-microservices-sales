package com.dp.userservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("application.keycloak")
public class KeycloakProperties {
    private String server;
    private String realm;
    private String clientId;
    private String clientSecret;
    private String grantType = "password";
}
