package com.dp.deliveryservice.service;

import com.dp.deliveryservice.persistence.entity.DeliveryCommissionSettings;
import com.dp.deliveryservice.persistence.repository.DeliveryCommissionSettingsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeliveryCommissionSettingsServiceImpl implements DeliveryCommissionSettingsService {

    private final DeliveryCommissionSettingsRepository repository;

    @Override
    public DeliveryCommissionSettings getSettings() {
        return repository.findFirstBy().orElseGet(() -> {
            log.info("commission settings not found, creating default");
            return repository.save(createDefaultSettings());
        });
    }

    @Override
    public DeliveryCommissionSettings saveSettings(DeliveryCommissionSettings newSettings) {
        log.info("saving new commission settings");
        newSettings.setId(1L);
        return repository.save(newSettings);
    }

    private DeliveryCommissionSettings createDefaultSettings() {
        return new DeliveryCommissionSettings()
                .setId(1L)
                .setCommissionSetting(10.0)
                .setProcessingSetting(10.0)
                .setInsuranceSetting(10.0)
                .setSeaDeliveryFeeSetting(10.0)
                .setAirDeliveryFeeSetting(10.0);
    }
}
