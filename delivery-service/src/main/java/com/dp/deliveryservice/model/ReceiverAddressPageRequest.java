package com.dp.deliveryservice.model;

public record ReceiverAddressPageRequest(
        Long id,
        String name,
        Long userId,
        String country,
        String destinationType,
        String receiverData,  // first + last name + phone
        String region,
        String city,
        String house,
        String apartment,
        Integer page,
        Integer size
) {
    public ReceiverAddressPageRequest {
        page = page == null ? 0 : page;
        size = size == null ? 10 : size;
    }
}
