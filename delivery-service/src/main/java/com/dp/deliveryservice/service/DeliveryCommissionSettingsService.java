package com.dp.deliveryservice.service;

import com.dp.deliveryservice.persistence.entity.DeliveryCommissionSettings;

public interface DeliveryCommissionSettingsService {
    DeliveryCommissionSettings getSettings();
    DeliveryCommissionSettings saveSettings(DeliveryCommissionSettings newSettings);
}
