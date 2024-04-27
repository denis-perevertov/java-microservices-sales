package com.dp.deliveryservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
public class RestClientConfig {

    private final RestClient.Builder builder;
    private final String API_GATEWAY_URL = "http://localhost:8081";

    @Bean
    public RestClient webClient() {
        return builder
                .baseUrl(API_GATEWAY_URL)
                .build();
    }
}
