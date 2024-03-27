package com.dp.deliveryservice.model;

public record ReceiverAddressCreateRequest(
        Long userId,
        String name,
        String countryCode,
        String deliveryDestinationType,
        Receiver receiver,
        NPLocation region,
        NPLocation city,
        NPLocation warehouse,
        NPLocation street,
        String houseNumber,
        String apartmentNumber
) {
    public record Receiver (
            String firstName,
            String lastName,
            String phone
    ) {}

    public record NPLocation (
            String id,
            String name
    ) {}
}
