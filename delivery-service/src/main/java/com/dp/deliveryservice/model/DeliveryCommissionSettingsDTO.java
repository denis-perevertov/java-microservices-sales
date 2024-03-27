package com.dp.deliveryservice.model;

import jakarta.validation.constraints.Min;

public record DeliveryCommissionSettingsDTO(
        @Min(0)
        double commissionSetting,
        @Min(0)
        double insuranceSetting,
        @Min(0)
        double processingSetting,
        @Min(0)
        double airDeliveryFeeSetting,
        @Min(0)
        double seaDeliveryFeeSetting
) {
}
