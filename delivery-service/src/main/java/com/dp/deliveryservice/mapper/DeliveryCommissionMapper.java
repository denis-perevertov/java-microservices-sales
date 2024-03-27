package com.dp.deliveryservice.mapper;

import com.dp.deliveryservice.model.DeliveryCommissionSettingsDTO;
import com.dp.deliveryservice.persistence.entity.DeliveryCommissionSettings;
import com.dp.deliveryservice.service.DeliveryCommissionSettingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeliveryCommissionMapper {

    private final DeliveryCommissionSettingsService deliveryCommissionSettingsService;

    public DeliveryCommissionSettings dtoToSettings(DeliveryCommissionSettingsDTO dto) {
        DeliveryCommissionSettings settings = deliveryCommissionSettingsService.getSettings();
        settings.setCommissionSetting(dto.commissionSetting())
                .setInsuranceSetting(dto.insuranceSetting())
                .setProcessingSetting(dto.processingSetting())
                .setAirDeliveryFeeSetting(dto.airDeliveryFeeSetting())
                .setSeaDeliveryFeeSetting(dto.seaDeliveryFeeSetting());
        return settings;
    }

    public DeliveryCommissionSettingsDTO settingsToDTO(DeliveryCommissionSettings settings) {
        return new DeliveryCommissionSettingsDTO(
                settings.getCommissionSetting(),
                settings.getInsuranceSetting(),
                settings.getProcessingSetting(),
                settings.getAirDeliveryFeeSetting(),
                settings.getSeaDeliveryFeeSetting()
        );
    }
}
