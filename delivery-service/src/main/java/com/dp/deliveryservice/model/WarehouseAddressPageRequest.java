package com.dp.deliveryservice.model;

public record WarehouseAddressPageRequest(
        Long id,
        String street,
        String city,
        String state,
        String zip,
        String phone,
        Integer page,
        Integer size
) {
    public WarehouseAddressPageRequest {
        page = page == null ? 0 : page;
        size = size == null ? 10 : size;
    }
}
