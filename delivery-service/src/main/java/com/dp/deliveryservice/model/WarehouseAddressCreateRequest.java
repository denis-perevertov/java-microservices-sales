package com.dp.deliveryservice.model;

public record WarehouseAddressCreateRequest(
        String street,
        String city,
        String state,
        String zip,
        String phone
) {
}
