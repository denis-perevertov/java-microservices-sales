package com.dp.apigateway.config;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.factory.TokenRelayGatewayFilterFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("payment_service_route", r -> r
                        .path("/api/payment/**")
                        .filters(f -> f.stripPrefix(2))
                        .uri("lb://payment-service"))
                .route("product_service_route", r -> r
                        .path("/api/product", "/api/product/**")
                        .filters(f -> f.stripPrefix(2).tokenRelay())
                        .uri("lb://product-service"))
                .route("discovery_service_route", r -> r
                        .path("/eureka/web")
                        .filters(f -> f.setPath("/"))
                        .uri("http://localhost:8761"))
                .route("discovery_service_resources_route", r -> r
                        .path("/eureka/**")
                        .uri("http://localhost:8761"))
                .build();
    }
}
