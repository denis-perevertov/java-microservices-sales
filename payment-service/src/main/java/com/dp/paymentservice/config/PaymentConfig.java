package com.dp.paymentservice.config;

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Environment;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("application.braintree")
public class PaymentConfig {

    private String merchantId;
    private String publicKey;
    private String privateKey;

    @Bean
    public BraintreeGateway braintreeGateway() {
        return new BraintreeGateway(
                Environment.SANDBOX,
                merchantId,
                publicKey,
                privateKey
        );
    }
}
