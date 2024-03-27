package com.dp.orderservice.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OrderCreateRequest(
        @NotNull
        @Min(0)
        Long userId,
        @NotBlank
        String orderType,
        @NotBlank
        String orderStatus,
        @NotEmpty
        List<Product> products,
        @NotBlank
        String deliveryMethod,
        @Min(0)
        Long warehouseAddressId,
        @NotNull
        @Min(0)
        Long receiverAddressId
) {
    public record Product(
            Long id,
            String link,
            String trackingNumber,
            int quantity,
            List<String> extraServices
    ){}
}
