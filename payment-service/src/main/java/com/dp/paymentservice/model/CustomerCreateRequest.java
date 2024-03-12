package com.dp.paymentservice.model;

public record CustomerCreateRequest(
        String userId,
        String firstName,
        String lastName,
        String company,
        String email,
        String fax,
        String phone,
        String website,
        String paymentMethodNonce
) {
}
