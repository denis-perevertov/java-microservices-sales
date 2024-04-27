package com.dp.userservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Autowired
    private RestClient.Builder clientBuilder;

    @Bean
    public RestClient restClient() {
        return clientBuilder
                .baseUrl("http://localhost:8081")
                .build();
    }
}
