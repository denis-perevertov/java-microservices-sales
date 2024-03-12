package com.dp.paymentservice.config;

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Environment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
// @ConfigurationProperties
public class PaymentConfig {

    @Bean
    public BraintreeGateway braintreeGateway() {
        return new BraintreeGateway(
                Environment.SANDBOX,
                "5zqrszjkby8zd8yn",
                "byyybwvjpwyyqq7f",
                "aac880bc1e68e72b384db914e2367a28"
        );
    }
}
